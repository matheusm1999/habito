-- Add version column for optimistic locking and ensure updated_at exists
ALTER TABLE habit ADD COLUMN version INTEGER NOT NULL DEFAULT 0;
UPDATE habit SET version = 0 WHERE version IS NULL;
