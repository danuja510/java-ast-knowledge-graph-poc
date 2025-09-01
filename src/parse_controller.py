"""
Java AST Parser for EmployeeController.java using tree-sitter

This script parses a Java file and displays its Abstract Syntax Tree (AST)
using the tree-sitter library with the tree-sitter-java grammar.
"""

import os
from tree_sitter import Parser, Language
import tree_sitter_java

# Set up the parser with Java language
java_language = Language(tree_sitter_java.language())
parser = Parser(java_language)

# Path to the Java file (adjust if necessary)
file_path = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), 
    "java/employee-crud/src/main/java/com/java_ast_knowledge_graph_poc/employee_crud/controller/EmployeeController.java"
)

# Read the Java file
with open(file_path, "r", encoding="utf-8") as file:
    source_code = file.read()

# Parse the source code
tree = parser.parse(bytes(source_code, "utf-8"))

# Print basic information about the parse tree
print(f"Source file: {file_path}")
print(f"Root node type: {tree.root_node.type}")
print(f"Root node children count: {len(tree.root_node.children)}")

# Function to print the AST in a readable format
def print_node(node, level=0):
    indent = "  " * level
    node_text = node.text.decode('utf-8') if node.text else ''
    
    # Truncate very long node text for better readability
    if len(node_text) > 50:
        node_text = node_text[:47] + "..."
    
    # Replace newlines for cleaner output
    node_text = node_text.replace('\n', '\\n')
    
    print(f"{indent}{node.type}: '{node_text}'")
    
    for child in node.children:
        print_node(child, level + 1)

# Print the AST (limit depth for readability)
print("\nAST Structure (first 3 levels):")
def print_limited_node(node, level=0, max_level=3):
    if level > max_level:
        return
    indent = "  " * level
    node_text = node.text.decode('utf-8') if node.text else ''
    if len(node_text) > 50:
        node_text = node_text[:47] + "..."
    node_text = node_text.replace('\n', '\\n')
    print(f"{indent}{node.type}: '{node_text}'")
    for child in node.children:
        print_limited_node(child, level + 1, max_level)

print_limited_node(tree.root_node)

# Function to extract method information
def extract_methods(node):
    methods = []
    if node.type == "method_declaration":
        method_info = {"name": None, "annotations": [], "parameters": []}
        
        for child in node.children:
            if child.type == "identifier":
                method_info["name"] = child.text.decode('utf-8')
            elif child.type == "modifiers":
                for modifier_child in child.children:
                    if modifier_child.type == "annotation":
                        annotation_text = modifier_child.text.decode('utf-8')
                        method_info["annotations"].append(annotation_text)
        
        if method_info["name"]:
            methods.append(method_info)
    
    # Recursively check all children
    for child in node.children:
        methods.extend(extract_methods(child))
    
    return methods

# Extract and print controller methods
methods = extract_methods(tree.root_node)
print("\nController Methods:")
for method in methods:
    print(f"Method: {method['name']}")
    if method['annotations']:
        print(f"  Annotations: {', '.join(method['annotations'])}")
    print()

# Extract class name
def get_class_name(root_node):
    for node in root_node.children:
        if node.type == "class_declaration":
            for child in node.children:
                if child.type == "identifier":
                    return child.text.decode('utf-8')
    return None

class_name = get_class_name(tree.root_node)
print(f"Controller Class: {class_name}")
