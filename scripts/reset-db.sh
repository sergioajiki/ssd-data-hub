#!/bin/bash
DB_DIR="$(dirname "$0")/../data"

echo "Resetando banco H2..."
rm -f "$DB_DIR/datahub.mv.db"
rm -f "$DB_DIR/datahub.trace.db"
echo "Banco resetado. Suba a aplicacao para recriar o schema."
