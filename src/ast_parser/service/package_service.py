import logging
from config.constants import SCOPED_IDENTIFIER_AST_NODE_TYPE
from util import ast_util

# Configure logging
logger = logging.getLogger(__name__)

class PackageService:
    def __init__(self, graph_data_source):
        self.graph_data_source = graph_data_source
    
    def process_package_declaration_node(self, package_node):
        package_name_node = ast_util.find_child_by_type(package_node, SCOPED_IDENTIFIER_AST_NODE_TYPE)
        package_name = package_name_node.text.decode('utf-8')

        logger.info(f"Processing package declaration for: {package_name}")

        package_graph_node = self.retrieve_package_graph_node(package_name)

        if package_graph_node:
            logger.debug(f"Package '{package_name}' already exists in graph database")
        else:
            logger.info(f"Package '{package_name}' not found, creating new package node")
            package_graph_node = self.create_package_node(package_name)

        return package_graph_node
    
    def retrieve_package_graph_node(self, package_name):
        logger.debug(f"Searching for existing package: {package_name}")
        query = """
            MATCH (p:Package {name: $package_name})
            RETURN p
        """
        result = self.graph_data_source.execute_query(query, package_name=package_name)
        return result.records[0] if len(result.records) > 0 else None

    def create_package_node(self, package_name):
        logger.info(f"Creating new package node in graph database: {package_name}")
        query = """
            CREATE (p:Package {name: $package_name})
            RETURN p
        """
        result = self.graph_data_source.execute_query(query, package_name=package_name)
        package_node = result.records[0].get("p") if len(result.records) > 0 else None

        if package_node:
            logger.info(f"Successfully created package node: {package_name}")
        else:
            logger.error(f"Failed to create package node: {package_name}")
        
        return package_node
