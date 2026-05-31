-- Create model_configurations table
CREATE TABLE model_configurations (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    provider VARCHAR(100) NOT NULL,
    cost_per_1k_tokens DECIMAL(10, 6) NOT NULL,
    max_tokens INTEGER NOT NULL,
    capabilities TEXT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on provider for faster lookups
CREATE INDEX idx_model_configurations_provider ON model_configurations(provider);

-- Create index on enabled for filtering enabled models
CREATE INDEX idx_model_configurations_enabled ON model_configurations(enabled);
