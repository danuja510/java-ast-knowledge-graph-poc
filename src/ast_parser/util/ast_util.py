from exception.ast_node_not_found import ASTNodeNotFoundError


def find_child_by_type(node, child_type):
    child = next(
        (child for child in node.children if child.type == child_type),
        None
    )
    if child is None:
        raise ASTNodeNotFoundError(f"Child node of type '{child_type}' not found")
    return child

def find_children_by_type(node, child_type):
    return [child for child in node.children if child.type == child_type]
