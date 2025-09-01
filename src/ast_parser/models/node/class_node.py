from node import Node
from ast_parser.config.constants import CLASS_NODE_LABEL

class ClassNode(Node):
    def __init__(self, __name):
        super().__init__(CLASS_NODE_LABEL, __name)