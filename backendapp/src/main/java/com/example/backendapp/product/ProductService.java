package com.example.backendapp.product;

import com.example.backendapp.UserRepository;
import com.example.backendapp.product.Product.ProductMaintenance;
import com.example.backendapp.product.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Product> getAllProducts() {
        List<ProductDTO> products = productRepository.findAllActiveProducts();
        return products.stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long productId) {
        var product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("The product with %s, does not exist", productId)
            );
        }
        return mapToProduct(product.get());
    }

    public Long createProduct(ProductRequest productRequest) {
        var requestedUser = productRequest.getUserName();
        if (isUserAdmin(requestedUser)) {
            var productDTO = mapToProductDTO(productRequest.getProduct().getProductMaintenance(), null, "create", requestedUser);
            var savedProductEntity = productRepository.save(productDTO);
            return savedProductEntity.getProductId();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    requestedUser + " cannot create a product, Only allowed for admins"
            );
        }
    }

    public Long updateProduct(Long productId, ProductRequest productRequest) {
        var requestedUser = productRequest.getUserName();

        if (!isUserAdmin(requestedUser)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    requestedUser + " cannot update a product, Only allowed for admins"
            );
        }

        var existingProduct = productRepository.findById(productId);

        if (existingProduct.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The product does not already exist"
            );
        }

        var updatedProductDTO = mapToProductDTO(productRequest.getProduct().getProductMaintenance(), productId, "update", requestedUser);
        var updatedProduct = productRepository.save(updatedProductDTO);
        return updatedProduct.getProductId();
    }

    public Boolean deleteProduct(Long productId) {
        var existingProduct = productRepository.findById(productId);
        if (existingProduct.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The product does not already exist"
            );
        }

        var productToDelete = existingProduct.get();
        productToDelete.setProductActiveStatus(false);

        productRepository.save(productToDelete);

        return true;
    }

    private Product mapToProduct(ProductDTO productDTO) {
        return new Product(
                productDTO.getProductId(),
                new ProductMaintenance(
                        productDTO.getProductCode(),
                        productDTO.getProductMaterialType(),
                        productDTO.getMaterialDescription(),
                        productDTO.getBarcode(),
                        productDTO.getLength(),
                        productDTO.getWidth(),
                        productDTO.getHeight(),
                        productDTO.getDimension(),
                        productDTO.getWeight(),
                        productDTO.getSupplierName(),
                        productDTO.getPrice(),
                        productDTO.getCurrency(),
                        productDTO.getVelocity(),
                        productDTO.getInsertedUser(),
                        productDTO.getInsertedTimestamp(),
                        productDTO.getLastModifiedUser(),
                        productDTO.getLastModifiedtimestamp(),
                        true
                )
        );
    }

    private ProductDTO mapToProductDTO(ProductMaintenance productMaintenance, Long productId, String operation, String userName) {
        if (Objects.equals(operation, "create")) {
            return new ProductDTO(
                    null, // this should be auto-generated for create
                    productMaintenance.getProductCode(),
                    productMaintenance.getProductMaterialType(),
                    productMaintenance.getMaterialDescription(),
                    productMaintenance.getBarcode(),
                    productMaintenance.getLength(),
                    productMaintenance.getWidth(),
                    productMaintenance.getHeight(),
                    productMaintenance.getWeight(),
                    productMaintenance.getDimension(),
                    productMaintenance.getSupplierName(),
                    productMaintenance.getPrice(),
                    productMaintenance.getCurrency(),
                    productMaintenance.getVelocity(),
                    userName,
                    Timestamp.from(Instant.now()),
                    productMaintenance.getLastModifiedUser(),
                    productMaintenance.getLastModifiedtimestamp(),
                    true
            );
        }
        return new ProductDTO(
                productId,
                productMaintenance.getProductCode(),
                productMaintenance.getProductMaterialType(),
                productMaintenance.getMaterialDescription(),
                productMaintenance.getBarcode(),
                productMaintenance.getLength(),
                productMaintenance.getWidth(),
                productMaintenance.getHeight(),
                productMaintenance.getWeight(),
                productMaintenance.getDimension(),
                productMaintenance.getSupplierName(),
                productMaintenance.getPrice(),
                productMaintenance.getCurrency(),
                productMaintenance.getVelocity(),
                productMaintenance.getInsertedUser(),
                productMaintenance.getInsertedTimestamp(),
                userName,
                Timestamp.from(Instant.now()),
                true
        );
    }

    private Boolean isUserAdmin(String userName) {
        var user = userRepository.findByUserName(userName);
        return user.map(value -> value.getRole().equals("admin")).orElse(false);
    }

}
