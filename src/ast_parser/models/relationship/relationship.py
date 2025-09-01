from abc import ABC
from ast_parser.models.node.node import Node

class Relationship(ABC):

    def __init__(self, __label: str, source: Node, target: Node):
        self.__label = __label
        self.__source = source
        self.__target = target

    def get_label(self):
        return self.__label
    
    def get_source(self):
        return self.__source

    def get_target(self):
        return self.__target
