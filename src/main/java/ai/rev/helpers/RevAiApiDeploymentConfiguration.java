package ai.rev.helpers;

import java.util.HashMap;
import java.util.Map;

public class RevAiApiDeploymentConfiguration {

    // Enum for API deployment regions
    public enum RevAiApiDeployment {
        US("US"),
        EU("EU");

        private final String value;

        RevAiApiDeployment(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Configuration map for deployment URLs
    private static final Map<RevAiApiDeployment, DeploymentConfig> RevAiApiDeploymentConfigMap;

    static {
        RevAiApiDeploymentConfigMap = new HashMap<>();
        RevAiApiDeploymentConfigMap.put(
            RevAiApiDeployment.US,
            new DeploymentConfig("https://api.rev.ai", "wss://api.rev.ai")
        );
        RevAiApiDeploymentConfigMap.put(
            RevAiApiDeployment.EU,
            new DeploymentConfig("https://ec1.api.rev.ai", "wss://ec1.api.rev.ai")
        );
    }

    // Inner class for deployment configurations
    public static class DeploymentConfig {
        private final String baseUrl;
        private final String baseWebsocketUrl;

        public DeploymentConfig(String baseUrl, String baseWebsocketUrl) {
            this.baseUrl = baseUrl;
            this.baseWebsocketUrl = baseWebsocketUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public String getBaseWebsocketUrl() {
            return baseWebsocketUrl;
        }
    }

    // Method to get the configuration for a specific deployment
    public static DeploymentConfig getConfig(RevAiApiDeployment deployment) {
        return RevAiApiDeploymentConfigMap.get(deployment);
    }
}
