from abc import ABC

class Node(ABC):

    def __init__(self, __label: str, __name: str):
        self.__label = __label
        self.__name = __name

    
    def get_label(self):
        return self.__label

    def get_name(self):
        return self.__name