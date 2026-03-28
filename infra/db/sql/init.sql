CREATE SCHEMA IF NOT EXISTS healenium AUTHORIZATION healenium_user;

GRANT USAGE, CREATE ON SCHEMA healenium TO healenium_user;
ALTER ROLE healenium_user SET search_path TO healenium,public;
ALTER DATABASE healenium SET search_path TO healenium,public;