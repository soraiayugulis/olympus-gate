-- Create selection_rules table
CREATE TABLE selection_rules (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    priority INTEGER NOT NULL,
    condition_type VARCHAR(50) NOT NULL,
    condition_operator VARCHAR(50) NOT NULL,
    condition_value TEXT NOT NULL,
    target_model_id VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on priority for sorting rules
CREATE INDEX idx_selection_rules_priority ON selection_rules(priority);

-- Create index on enabled for filtering enabled rules
CREATE INDEX idx_selection_rules_enabled ON selection_rules(enabled);

-- Create index on target_model_id for finding rules by model
CREATE INDEX idx_selection_rules_target_model_id ON selection_rules(target_model_id);
