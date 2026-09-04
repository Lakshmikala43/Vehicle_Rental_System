import os

source_exts = {'.java', '.js', '.py'}
total_lines = 0
file_count = 0

for root, dirs, files in os.walk('.'):
    if '.git' in root or 'test' in root or 'node_modules' in root or 'target' in root:
        continue
    for f in files:
        ext = os.path.splitext(f)[1].lower()
        if ext in source_exts:
            fp = os.path.join(root, f)
            try:
                with open(fp, 'r', encoding='utf-8', errors='ignore') as fp_obj:
                    lines = len(fp_obj.readlines())
                    total_lines += lines
                    file_count += 1
            except Exception:
                pass

print(f"Source Files (.java, .js, .py): {file_count}, Total Source Lines: {total_lines}")
