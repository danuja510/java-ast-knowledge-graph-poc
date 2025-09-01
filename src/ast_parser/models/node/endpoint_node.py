from node import Node
from ast_parser.config.constants import ENDPOINT_NODE_LABEL

class EndpointNode(Node):
    def __init__(self, __name: str):
        super().__init__(ENDPOINT_NODE_LABEL, __name)
