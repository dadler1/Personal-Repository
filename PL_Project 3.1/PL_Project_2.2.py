# Author:         Daniel Adler
# Date:           09/09/2020
# Project:        Programming Languages Project: Phase 1.1
# Description:    Scans in a programming language script and assigns each token a value

import re
import sys

input_filepath = "template.txt"
output_filepath = "output.txt"


class Node:

    def __init__(self, data):
        self.data = data
        self.left = None
        self.middle = None
        self.right = None

    def __str__(self):
        return str(self.data)

    def get_token(self):
        return self.data[0]

    def get_type(self):
        return self.data[1]

    def get_left(self):
        return self.left

    def get_middle(self):
        return self.middle

    def get_right(self):
        return self.right

    def set_left(self, new_left):
        if type(new_left) is tuple:
            self.left = Node(new_left)
            return
        self.left = new_left

    def set_middle(self, new_middle):
        if type(new_middle) is tuple:
            self.middle = Node(new_middle)
            return
        self.middle = new_middle

    def set_right(self, new_right):
        if type(new_right) is tuple:
            self.right = Node(new_right)
            return
        self.right = new_right

    def print_tree(self, num_of_tabs=0):
        if self.data is None:
            return
        for n in range(num_of_tabs):
            print("\t", end="")
        print(self.data[0], ":", self.data[1])
        if self.left is not None:
            self.left.print_tree(num_of_tabs + 1)
        if self.middle is not None:
            self.middle.print_tree(num_of_tabs + 1)
        if self.right is not None:
            self.right.print_tree(num_of_tabs + 1)

    def write_tree(self, num_of_tabs=0):
        if self.data is None:
            return
        f = open("output.txt", 'a+')
        myString = ""
        for n in range(num_of_tabs):
            myString += "\t"
        myString += self.data[0] + " : " + self.data[1] + "\n"
        f.write(myString)
        f.close()
        if self.left is not None:
            self.left.write_tree(num_of_tabs + 1)
        if self.middle is not None:
            self.middle.write_tree(num_of_tabs + 1)
        if self.right is not None:
            self.right.write_tree(num_of_tabs + 1)


# Function Name:    read_data_from_file()
# Parameters:       n/a
# Description:      Reads template.txt line by line into an array for further processing
# Returns:
#                   file_data: This represents the raw input given by the lines, it exists for viewing purposes.
#                   raw_data:  This represents the cleaned data from each line - excluding whitespaces and newline
#                              characters.
def read_data_from_file():
    f = open(input_filepath, 'r')
    file_data = []
    clean_data = []
    while True:
        line = f.readline()
        file_data.append(line.strip("\n"))
        if not line:
            break
        this_line = line.split(" ")
        clean_line = []
        for big_token in this_line:
            if big_token.isspace():
                continue
            if len(big_token) == 0:
                continue
            clean_line.append(big_token.strip())
        if len(clean_line) == 0:
            continue
        clean_data.append(clean_line)

    # print(clean_data)
    return file_data, clean_data


# Function Name:    get_scanned_data(raw_data)
# Parameters:       raw_data:  This represents the list of lines within the input file that have been cleaned
#                   original_data: This represents the list of lines that remained unmodified from the original file.
# Description:      Uses Regex to interpret each individual keyword within this code and assign it a value.
# Returns:          out_map: A list of tuples for each line within the code in the format <keyword,type>
def get_scanned_data(raw_data, original_data):
    cur_index = 0
    # Clear the file for new compilation
    f = open("output.txt", "w")
    f.write("OUTPUT DATA:\n")
    f.close()
    # Begin writing into file
    out_map = []
    for cur_line in raw_data:
        line_data = []
        if original_data[cur_index] is not None:
            f = open("output.txt", "a")
            f.write("\n" + "LINE: " + original_data[cur_index] + "\n\n")
            f.close()
            for cur_token in cur_line:
                token_data, last_flag = scan_token(cur_token)
                line_data += token_data
                if last_flag == 'ERROR':
                    break
            f = open("output.txt", "a")
            f.write("\n")
            f.close()
            out_map.append(line_data)
        cur_index += 1
    # print(out_map)
    return out_map


def scan_token(token):
    f = open("output.txt", 'a')
    return_data = []
    last_flag = "VALID"
    if is_punc(token):
        return_data.append((token, 'PUNCTUATION'))
        f.write(str(return_data)[2:-2] + '\n')
        # print(str(return_data) + " PUNC")
    elif is_key(token):
        return_data.append((token, 'KEYWORD'))
        f.write(str(return_data)[2:-2] + '\n')
        # print(str(return_data) + " KEYWORD")
    elif is_id(token):
        return_data.append((token, 'IDENTIFIER'))
        f.write(str(return_data)[2:-2] + '\n')
        # print(str(return_data) + " IDENTIFIER")
    elif is_num(token):
        return_data.append((token, 'NUMBER'))
        f.write(str(return_data)[2:-2] + '\n')
        # print(str(return_data) + " NUMBER")
    else:
        cur_index = len(token)
        start_index = 0
        while start_index < cur_index:
            this_token = token[start_index:cur_index]
            # print(this_token)
            token_status = is_in_language(this_token)
            # print(token_status)
            if token_status == 'ERROR':
                if len(this_token) == 1:
                    return_data.append((this_token, token_status))
                    last_flag = 'ERROR'
                    break
                cur_index -= 1
                continue
            return_data.append((this_token, token_status))
            start_index = cur_index
            cur_index = len(token)
        for item in return_data:
            f.write(str(item)[1:-1] + '\n')
        f.close()
    return return_data, last_flag


def is_in_language(my_string):
    if is_key(my_string):
        return 'KEYWORD'
    elif is_id(my_string):
        return 'IDENTIFIER'
    elif is_num(my_string):
        return 'NUMBER'
    elif is_punc(my_string):
        return 'PUNCTUATION'
    return 'ERROR'


def is_id(my_string):
    IDENTIFIER = "^([a-z]|[A-Z])([a-z]|[A-Z]|[0-9])*$"
    return re.match(IDENTIFIER, my_string) is not None


def is_num(my_string):
    NUMBER = "^[0-9]+$"
    return re.match(NUMBER, my_string) is not None


def is_par(my_string):
    PARENTHESES = "^(\(|\))$"
    return re.match(PARENTHESES, my_string) is not None


def is_punc(my_string):
    PUNCTUATION = r"^(\+|\-|\*|/|\(|\)|\:=|\;)$"
    return re.match(PUNCTUATION, my_string) is not None


def is_key(my_string):
    KEYWORD = "^(if|then|else|endif|while|do|endwhile|skip)$"
    return re.match(KEYWORD, my_string) is not None


def nodify_data(data):  # 2d array of tuples
    nodes = []
    for line in data:
        line_nodes = []
        for token in line:
            current_node = Node(token)
            line_nodes.append(current_node)
        nodes.append(line_nodes)
    return nodes


def parse_full_language(data):  # 2d array of tuples
    nodes = nodify_data(data)
    compressed_nodes = compress_expr_and_asgn(nodes)
    new_nodes = parse_language(compressed_nodes)
    new_nodes.write_tree()
    return new_nodes


def parse_language(nodes):
    index = 0
    flag = 'd'  # d = default, i = if statement, w = while loop
    start_index = -1
    while index < len(nodes):
        if nodes[index].get_token() == 'if':
            flag = 'i'
            start_index = index
        if nodes[index].get_token() == 'while':
            start_index = index
            flag = 'w'
        if nodes[index].get_token() == 'endif' and flag == 'i':
            temp = nodes[0:start_index] + [parse_if(nodes[start_index:index + 1])] + nodes[index + 1:len(nodes)]
            return parse_language(temp)
        if nodes[index].get_token() == 'endwhile' and flag == 'w':
            temp = nodes[0:start_index] + [parse_while(nodes[start_index:index])] + nodes[index + 1:len(nodes)]
            return parse_language(temp)
        index += 1
    if flag != 'd':
        print("Invalid Input: If Statement or While Loop does not follow correct syntax.")
        sys.exit()
    return parse_semicolons(nodes)


def parse_if(nodes):
    # Check that format is correct
    if nodes[1].get_type() == 'KEYWORD' or nodes[2].get_token() != 'then':
        print("Invalid Input: If Statement does not follow correct syntax.")
        sys.exit()
    else_location = -1
    for i in range(3, len(nodes)):
        if nodes[i].get_token() == 'else':
            else_location = i
            if nodes[i + 1].get_token() == 'endif':
                print("Invalid Input: Else statement is empty.")
                sys.exit()
    if else_location == -1:
        print("Invalid Input: Else statement does not exist.")
        sys.exit()
    root_node = Node(('IF', 'KEYWORD'))
    root_node.left = nodes[1]
    root_node.middle = parse_semicolons(nodes[3:else_location])
    root_node.right = parse_semicolons(nodes[else_location + 1:len(nodes) - 1])
    return root_node


def parse_while(nodes):
    # Check that format is correct
    if nodes[1].get_type() == 'KEYWORD' or nodes[2].get_token() != 'do':
        print("Invalid Input: If Statement does not follow correct syntax.")
        sys.exit()
    root_node = Node(('WHILE', 'KEYWORD'))
    root_node.left = nodes[1]
    root_node.right = parse_semicolons(nodes[3:len(nodes)])
    return root_node


def parse_semicolons(nodes):
    for index, token in enumerate(nodes):
        if token.get_token() == ';' and token.right is None and token.left is None:
            try:
                leftNode = nodes[index - 1]
            except IndexError:
                leftNode = None
            try:
                rightNode = nodes[index + 1]
            except IndexError:
                rightNode = None
            token.set_left(leftNode)
            token.set_right(rightNode)
            temp_arr = nodes[0:index - 1] + [token] + nodes[index + 2:len(nodes)]
            return parse_semicolons(temp_arr)
    return nodes[0]


def compress_expr_and_asgn(data):  # 2D array of nodes
    compressed_data = []
    for line, line_data in enumerate(data):
        cur_index = 0
        while cur_index < len(line_data):
            if line_data[cur_index].get_token() == 'endif' or line_data[cur_index].get_token() == 'endwhile':
                if cur_index != len(line_data) - 1:
                    if line_data[cur_index + 1].get_token() != ';':
                        print("Error - Missing semicolon after " + str(line_data[cur_index].get_token()))
                        sys.exit()

            if line_data[cur_index].get_type() == 'KEYWORD' or line_data[cur_index].get_token() == ';':
                compressed_data.append(line_data[cur_index])
                cur_index += 1
                continue
            else:
                start_index = cur_index
                final_index = len(line_data)
                is_asgn = False
                for i in range(start_index, len(line_data)):
                    if line_data[i].get_token() == ':=':
                        is_asgn = True
                    if line_data[i].get_token() == ';' or line_data[i].get_type() == 'KEYWORD':
                        final_index = i
                cur_index = final_index
                if line_data[start_index].get_type() != 'IDENTIFIER':
                    is_asgn = False
                if is_asgn:
                    compressed_data.append(parse_assignment(line_data[start_index:final_index], line))
                    continue
                compressed_data.append(parsify_expression(line_data[start_index:final_index], line))
    return compressed_data


def parse_assignment(line_data, line):
    if line_data[0].get_type() == 'IDENTIFIER' and line_data[1].get_token() == ':=':
        head = line_data[1]
        head.set_left(line_data[0])
        head.set_right(parsify_expression(line_data[2:], line))
        return head
    print("Invalid Input - Expected Assignment follows incorrect format on line " + str(line + 1) + ".")
    sys.exit()


def parsify_expression(line_nodes, line):
    open_count = 0
    for token in line_nodes:
        if token.get_type() == 'KEYWORD' or token.get_token() == ':=' or token.get_token() == ';':
            print("Invalid Input - Expression has unexpected symbols on line " + str(line + 1) + ".")
            sys.exit()
            # line_nodes.append(Node(token))
        if token.get_token() == '(':
            open_count += 1
        if token.get_token() == ')':
            open_count -= 1
        if open_count < 0:
            print("Invalid Input - Misplaced parentheses on line " + str(line + 1) + ".")
            sys.exit()
    if is_punc(line_nodes[0].get_token()) and not is_par(line_nodes[0].get_token()):
        print("Invalid_Input - Expression on line " + str(line + 1) + " begins with invalid Token.")
        sys.exit()
    if is_punc(line_nodes[len(line_nodes) - 1].get_token()) and not is_par(line_nodes[len(line_nodes) - 1].get_token()):
        print("Invalid_Input - Expression on line " + str(line + 1) + " ends with invalid Token.")
        sys.exit()
    if len(line_nodes) > 1:
        for index in range(0, len(line_nodes) - 1):
            cur_token = line_nodes[index]
            next_token = line_nodes[index + 1]
            if cur_token.get_type() == next_token.get_type():
                # print(cur_token.get_token(), next_token.get_token())
                if is_punc(cur_token.get_token()) and cur_token.get_token() != ')' and next_token.get_token() == '(':
                    continue
                if cur_token.get_token() == ')' and is_punc(next_token.get_token()) and next_token.get_token() != '(':
                    continue
                print("Invalid Input - Two unaccepted consecutive symbols of the same type on line " + str(
                    line + 1) + ".")
                sys.exit()
            if (is_num(cur_token.get_type()) or is_id(cur_token.get_type()) or is_key(
                    cur_token.get_type())) and next_token.get_token() == '(':
                print("Invalid Input - Unexpected token type before \'(\' on line " + str(line + 1) + ".")
                sys.exit()
            if (is_num(next_token.get_type()) or is_id(next_token.get_type()) or is_key(
                    next_token.get_type())) and cur_token.get_token() == ')':
                print("Invalid Input - Unexpected token type after \')\' on line " + str(line + 1) + ".")
                sys.exit()

    if open_count != 0:
        print("Invalid Input - Unequal amount of opening and closing parentheses on line " + str(line + 1) + ".")
        sys.exit()
    linedata = parse_expr(line_nodes)
    # linedata.print_tree()
    # linedata.write_tree()
    return linedata


def parse_expr(line_data):
    start_index = 0
    for index, token in enumerate(line_data):
        if token.get_token() == '(':
            start_index = index
        if token.get_token() == ')':
            temp = parse_mult(line_data[start_index + 1:index])
            temp_arr = line_data[0:start_index] + [temp] + line_data[index + 1:len(line_data)]
            return parse_expr(temp_arr)
    return parse_mult(line_data)


def parse_mult(line_data):
    for index, token in enumerate(line_data):
        if token.get_token() == '*' and token.right is None and token.left is None:
            leftNode = line_data[index - 1]
            rightNode = line_data[index + 1]
            token.set_left(leftNode)
            token.set_right(rightNode)
            temp_arr = line_data[0:index - 1] + [token] + line_data[index + 2:len(line_data)]
            return parse_mult(temp_arr)
    return parse_div(line_data)


def parse_div(line_data):
    for index, token in enumerate(line_data):
        if token.get_token() == '/' and token.right is None and token.left is None:
            leftNode = line_data[index - 1]
            rightNode = line_data[index + 1]
            token.set_left(leftNode)
            token.set_right(rightNode)
            temp_arr = line_data[0:index - 1] + [token] + line_data[index + 2:len(line_data)]
            return parse_div(temp_arr)
    return parse_sub(line_data)


def parse_sub(line_data):
    for index, token in enumerate(line_data):
        if token.get_token() == '-' and token.right is None and token.left is None:
            leftNode = line_data[index - 1]
            rightNode = line_data[index + 1]
            token.set_left(leftNode)
            token.set_right(rightNode)
            temp_arr = line_data[0:index - 1] + [token] + line_data[index + 2:len(line_data)]
            return parse_sub(temp_arr)
    return parse_add(line_data)


def parse_add(line_data):
    for index, token in enumerate(line_data):
        if token.get_token() == '+' and token.right is None and token.left is None:
            leftNode = line_data[index - 1]
            rightNode = line_data[index + 1]
            token.set_left(leftNode)
            token.set_right(rightNode)
            temp_arr = line_data[0:index - 1] + [token] + line_data[index + 2:len(line_data)]
            return parse_add(temp_arr)
    return line_data[0]


def evaluate_full_language(parse_tree):
    stack = accumulate_stack(parse_tree)
    if len(stack) > 1:
        print("Error Evaluating Tree")
        sys.exit()
    return stack


temp_stack = []


def accumulate_stack(parse_tree):
    temp_stack.append(Node((parse_tree.get_token(), parse_tree.get_type())))
    compress_stack()
    if parse_tree.get_left() is not None:
        accumulate_stack(parse_tree.get_left())
    if parse_tree.get_middle() is not None:
        accumulate_stack(parse_tree.get_middle())
    if parse_tree.get_right() is not None:
        accumulate_stack(parse_tree.get_right())
    return temp_stack


def compress_stack():
    if len(temp_stack) >= 3:
        if temp_stack[-1].get_type() == 'NUMBER' and temp_stack[-2].get_type() == 'NUMBER' and temp_stack[-3].get_type() == 'PUNCTUATION':
            try:
                switcher = {
                    '/': int(int(temp_stack[-2].get_token())/int(temp_stack[-1].get_token())),
                    '*': int(temp_stack[-2].get_token())*int(temp_stack[-1].get_token()),
                    '+': int(temp_stack[-2].get_token())+int(temp_stack[-1].get_token()),
                    '-': int(temp_stack[-2].get_token())-int(temp_stack[-1].get_token())
                }
            except ZeroDivisionError:
                print("Error: Tried to divide by Zero")
                sys.exit()
            try:
                result = switcher.get(temp_stack[-3].get_token())
            except KeyError:
                print("Error: Punctuation not acceptable in this phase of project.")
                sys.exit()
            temp_stack.pop()
            temp_stack.pop()
            temp_stack.pop()
            temp_stack.append(Node((str(result), 'NUMBER')))
            compress_stack()
    return


def main():
    my_data, clean_data = read_data_from_file()
    retrieved_data = get_scanned_data(clean_data, my_data)
    parse_tree = parse_full_language(retrieved_data)
    stack = evaluate_full_language(parse_tree)
    print(stack[0].get_token())


if __name__ == '__main__':
    main()
