import logging
from config.constants import PACKAGE_DECLARATION_AST_NODE_TYPE
from config.graph_data_source import GraphDataSource
from service.package_service import PackageService
from config.java_parser import JavaParser

import util.ast_util as ast_util

# Configure logging to see all log messages
logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)

# Reduce Neo4j logging noise
logging.getLogger('neo4j').setLevel(logging.WARNING)
logging.getLogger('neo4j.io').setLevel(logging.WARNING)
logging.getLogger('neo4j.pool').setLevel(logging.WARNING)

def main():
    """Main entry point for the AST parser application"""
    java_parser = JavaParser()
    graph_data_source = GraphDataSource("neo4j://localhost:7687", "neo4j", "password")
    package_service = PackageService(graph_data_source)

    with open("../../data/java/employee-crud/src/main/java/com/java_ast_knowledge_graph_poc/employee_crud/controller/EmployeeController.java", "r", encoding="utf-8") as file:
        source_code = file.read()

    ast = java_parser.parse(source_code)
    ast_root = ast.root_node

    package_declaration_ast_node = ast_util.find_child_by_type(ast_root, PACKAGE_DECLARATION_AST_NODE_TYPE)
    package_service.process_package_declaration_node(package_declaration_ast_node)

if __name__ == "__main__":
    main()