import json
import re

def process_file(filename):
    with open(filename, 'r', encoding='utf-8') as file:
        content = file.read()

    # Dividir el contenido en preguntas individuales
    questions = re.split(r'\n(?=¿)', content.strip())

    # Procesar cada pregunta
    processed_questions = []
    for question in questions:
        # Eliminar saltos de línea y espacios extra
        question = re.sub(r'\s+', ' ', question).strip()
        processed_questions.append(question)

    # Crear el JSON
    json_output = json.dumps(processed_questions, ensure_ascii=False, indent=2)

    # Guardar el JSON en un archivo
    with open('output.json', 'w', encoding='utf-8') as outfile:
        outfile.write(json_output)

    print("El archivo JSON ha sido creado como 'output.json'")

# Ejecutar el script
process_file('documents.txt')