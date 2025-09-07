from exception.ast_processing_error import ASTProcessingError


class ASTNodeNotFoundError(ASTProcessingError):
    """Raised when a required AST node is not found"""
    pass