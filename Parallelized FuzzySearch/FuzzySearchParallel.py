"""
*    Author:        Daniel Adler
*
*    Date:          04/05/2021
*
*    Program Name:  Serial Fuzzy Search (Python Edition)
*
*    Run Command:   python3 FuzzySearch.py <input file> <keyword> <match distance> <bash script>
*
*    Inputs:        input file      This is the file name of the large text to search for matches in
*                   keyword:        This is the string that the user wants to find matches for within the large text
*                   match distance: This integer value represents the maximum levenshtein distance you would like your
*                                   matches to have
*                   bash script:    This is an integer value 1 or 0 that represents whether you'd like for the results
*                                   of the search to only print out the time or not
*                   thread count:   This is the number of threads you would like this program to run on.
*
*    Description:   This program will execute the fuzzy search algorithm on any given text to locate any keyword
*                   that the user would like to locate.
"""


try:
    import sys          # Used to take in command line arguments
    import time         # Used to measure how long serial algorithm will take to run
    import threading    # Used to multithread in python
except ImportError:
    print("Error: One or more of the required libraries are not available.")
    exit(0)


matches = []
thread_times = {}
start = time.time()


"""
*    Function Name: get_input_file_data
*
*    Parameters:    file_path:  this is the name of the file you would like to read from
*
*    Return:        output:     this is a potentially really long string that includes all of the data within the 
*                               given text file.
*
*    Description:   this function will take a file name and will move all of the text from that file and move it into
*                   a very long string.
"""


def get_input_file_data(file_path):
    my_file = open(file_path, 'r')
    data = my_file.read()
    my_file.close()
    output = data.replace('\n', ' ')
    return output


"""
*    Function Name: fuzzy_search
*
*    Parameters:    keyword:      This is the word that the user would like to find matches for within their large text
*                   search_text:  This is the contents of the file that the user would like to search for their keyword
*                                 in
*                   max_distance: This is the maximum levenshtein distance that the user would like to find matches for.
*                                 A larger levenshtein distance would broaden the amount of matches available and could
*                                 significantly increase the runtime of this program.
*
*    Return:        matches:      This is an array of tuples that contains the levenshtein distances of every match of
*                                 the keyword within the search_text within levenshtein distance max_distance.
*
*    Description:   This function will take in the user's specified keyword, search text, and maximum levenshtein
*                   distance in order to compile a list of relevant matches.
"""


def fuzzy_search(keyword, search_text, max_distance, num_threads):

    # create indexes of results map in order to prevent KeyError

    for i in range(max_distance+1):
        matches.append([])

    # loop through the entire search text
    my_threads = []
    elapsed = 0
    for i in range(num_threads):

        my_threads.append(threading.Thread(target=thread_function, args=(keyword, search_text, max_distance, i,
                                                                         num_threads)))

    start = time.time()
    for i in range(num_threads):
        start = time.time()
        my_threads[i].start()
    end = time.time()
    elapsed += end - start

    for i in range(num_threads):
        my_threads[i].join()
        print(i, thread_times[i][0], thread_times[i][1])

    print(thread_times)
    print("Program time: ", elapsed)
    return None





"""
*    Function Name: thread_function
*
*    Parameters:    keyword:              word that the user wants to search for
*                   search_text:          the entire text the user wants to search through
*                   max_distance:         the largest levenshtein distance the user wants matches for
*                   my_rank:              the identifier for the current thread
*                   num_threads:          the total number of threads
*                   search_text_length:   the length of the search text in full
*                   keyword_length:       the length of the keyword
*                   min_length:           the minimum length that a match can be
*                   search_length:        the length of the search text that the loop must iterate through
*
*    Return:        None
*
*    Description    This function will search for all matches within the text between my_first_i and my_last_i
"""


def thread_function(keyword, search_text, max_distance, my_rank, num_threads):
    # Initialize all local variables
    search_text_length = len(search_text)
    keyword_length = len(keyword)
    min_length = keyword_length - max_distance
    search_length = search_text_length - (keyword_length - 1) + max_distance
    if min_length < 1:
        min_length = 1

    thread_start = time.time()
    # Calculate first and last i for each thread
    quotient = search_length // num_threads
    remainder = search_length % num_threads
    if my_rank < remainder:
        my_n_count = quotient + 1
        my_first_i = my_rank * my_n_count
    else:
        my_n_count = quotient
        my_first_i = my_rank * my_n_count + remainder
    my_last_i = my_first_i + my_n_count

    #print("First i: ", my_first_i, " Last i: ", my_last_i)


    # Complete processing for current thread

    while my_first_i < my_last_i:
        i = my_first_i
        # only work with strings that are potential candidates per max_distance
        first_index = i
        last_index = i + keyword_length + max_distance
        # ensure that the length of the word your checking does not cause an out of bounds error
        if last_index > search_text_length:
            last_index = search_text_length
        # figure out what the largest string starting at the current index could be
        largest_string = search_text[first_index:last_index+1]
        string_count = (last_index-first_index) - (keyword_length - max_distance) + 1
        # go through every possible string starting from point 'i' with lengths between keyword_length + max_distance
        # and keyword_length - max_distance
        for j in range(string_count):
            cur_string = largest_string[:min_length + j + 1]
            distance, match = levenshtein_distance(keyword.lower(), cur_string.lower())
            if distance > max_distance:
                continue
            #mutex.acquire()
            matches[distance].append((i, match))
            #mutex.release()
        my_first_i += 1
    thread_end = time.time()
    thread_time = thread_end - thread_start
    global start
    thread_times[my_rank] = (my_rank, thread_time)
    return None


"""
*    Function Name: levenshtein_distance
*
*    Parameters:    string1:    this is the string that is passed through the fuzzy search algorithm
*                   string2:    this is the subtext that string1 is currently being compared to within the large text
*
*    Return:        distance:   this is the levenshtein distance between string1 and string2
*                   string2:    this is the subtext that string1 was compared to
*
*    Description:   this function will take in two strings and return the levenshtein distance between them. The
*                   Levenshtein Distance of a string indicates the minimum number of substitutions, insertions,
*                   and deletions that would be required in order to transform string1 into string2.
"""


def levenshtein_distance(string1, string2):
    dist_table = [[0 for i in range(len(string2) + 1)] for j in range(len(string1) + 1)]
    # Initialising first row:
    for i in range(len(string1) + 1):
        dist_table[i][0] = i
    # Initialising first column:
    for j in range(len(string2) + 1):
        dist_table[0][j] = j
    # Comparing each part of string with each part of the other string
    for i in range(1, len(string1) + 1):
        for j in range(1, len(string2) + 1):
            if string1[i - 1] == string2[j - 1]:
                dist_table[i][j] = dist_table[i - 1][j - 1]
            else:
                # Adding 1 for cost of each operation
                insertion = 1 + dist_table[i][j - 1]
                deletion = 1 + dist_table[i - 1][j]
                substitution = 1 + dist_table[i - 1][j - 1]
                # Choosing the lowest number of transformations required to change string:
                dist_table[i][j] = min(insertion, deletion, substitution)
    # Collecting the levenshtein distance of the entire string
    distance = dist_table[len(string1)][len(string2)]
    return distance, string2


"""
*    Function Name: main
*
*    Parameters:    N/A
*
*    Returns:       0 or -1 depending on whether the program was successful or erroneous.
*
*    Description:   main method
"""


def main():
    if len(sys.argv) != 6:
        print("Error: Please run: python3 FuzzySearch.py <input file> <search keyword> <max levenshtein distance>"
              " <bash script boolean>")
        print("For more information on command line arguments, please review either script or README file.")
        return -1
    # Take in the input file
    input_file = sys.argv[1]
    try:
        file_test = open(input_file, 'r')
        file_test.close()
    except FileNotFoundError:
        print("Error: No such file was found.")
        return -1
    search_text = get_input_file_data(input_file)

    #        Take in keyword
    word = sys.argv[2]
    try:
        keyword = str(word)
    except TypeError:
        print("Error: keyword is invalid")
        return -1

    #        Take in maximum Levenshtein distance
    max_distance = int(sys.argv[3])
    if not (isinstance(max_distance, int)):
        print("Error: max_distance must be an integer")
        return -1
    if max_distance < 0:
        print("Error: max_distance must be >=0")
        return -1

    #        Take in bash script boolean-integer
    bash_script_boolean = int(sys.argv[4])
    if bash_script_boolean not in [0, 1]:
        print("Error: bash script (Argument 4) must be either 0 or 1")
        return -1
    print()

    thread_count = int(sys.argv[5])

    # Begin Fuzzy Search
    start_time = time.time()
    fuzzy_search(keyword, search_text, max_distance, thread_count)
    end_time = time.time()
    elapsed_time = end_time - start_time

    # Print Fuzzy Search Results
    if bash_script_boolean == 0:
        print("Fuzzy Search completed for keyword \'" + keyword + "\' within \'" + input_file + "\' with n = " +
              str(max_distance) + " in " + str(elapsed_time) + " seconds.\n")
        for i in range(max_distance+1):
            print("Distance = " + str(i) + ": " + str(matches[i]) + "\n")
        return 0
    elif bash_script_boolean == 1:
        print(elapsed_time)


if __name__ == '__main__':
    main()


