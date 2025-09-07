from tree_sitter import Parser, Language
import tree_sitter_java as tsj

class JavaParser:
    def __init__(self):
        language = Language(tsj.language())
        self.__parser = Parser(language=language)

    def parse(self, code: str):
        tree = self.__parser.parse(bytes(code, "utf8"))
        return tree