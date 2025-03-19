package com.example.backendapp.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GraphSource {

                private Map<String, Integer> keyValues = new HashMap<>();

                @JsonProperty("Website")
                public int getWebsite() {
                        return keyValues.getOrDefault("Website", 0);
                }

                @JsonProperty("Google")
                public int getGoogle() {
                        return keyValues.getOrDefault("Google", 0);
                }

                @JsonProperty("Direct")
                public int getDirect() {
                        return keyValues.getOrDefault("Direct", 0);
                }

                @JsonProperty("Argus")
                public int getArgus() {
                        return keyValues.getOrDefault("Argus", 0);
                }

                @JsonProperty("E-mag")
                public int getEMag() {
                        return keyValues.getOrDefault("E-mag", 0);
                }

                @JsonProperty("Key-account")
                public int getKeyAccount() {
                        return keyValues.getOrDefault("Key-account", 0);
                }

                @JsonProperty("Bucharest")
                public int getBucharest() {
                        return keyValues.getOrDefault("Bucharest", 0);
                }

                public Map<String, Integer> getKeyValues() {
                        return keyValues;
                }

                public void setKeyValues(Map<String, Integer> keyValues) {
                        this.keyValues = keyValues;
                }
        }
