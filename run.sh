#!/bin/bash

# Compile semua file di src/
find src -name "*.java" > sources.txt
javac -d bin -cp bin @sources.txt

# Hapus file sumber sementara
rm sources.txt

# Jalankan program utama
java -cp bin Main

# Hapus semua file .class dalam bin/
find bin -type f -name "*.class" -delete

# Hapus semua folder kosong dalam bin/
find bin -type d -empty -delete

# Cara penggunaan
# chmod +x run.sh
# ./run.sh