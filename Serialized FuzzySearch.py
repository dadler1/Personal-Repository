import os
import numpy as np
import time
import pdb


def calcBadValues(substring):
    length = len(substring)
    badvalues = {'a': length, 'b': length, 'c': length, 'd': length, 'e': length, 'f': length, 'g': length,
                 'h': length, 'i': length, 'j': length, 'k': length, 'l': length, 'm': length, 'n': length,
                 'o': length, 'p': length, 'q': length, 'r': length, 's': length, 't': length, 'u': length,
                 'v': length, 'w': length, 'x': length, 'y': length, 'z': length, 'A': length, 'B': length,
                 'C': length, 'D': length, 'E': length, 'F': length, 'G': length, 'H': length, 'I': length,
                 'J': length, 'K': length, 'L': length, 'M': length, 'N': length, 'O': length, 'P': length,
                 'Q': length, 'R': length, 'S': length, 'T': length, 'U': length, 'V': length, 'W': length,
                 'X': length, 'Y': length, 'Z': length, '!': length, '?': length, ',': length, '%': length,
                 '/': length, '(': length, ')': length, ' ': length, '_': length, '<': length, '>': length,
                 ':': length, ';': length, '.': length, '"': length, '1': length, '2': length, '3': length,
                 '4': length, '5': length, '6': length, '7': length, '8': length, '9': length, '0': length,
                 '@': length, '#': length, '$': length, '^': length, '&': length, '-': length, '+': length,
                 '=': length, '|': length, '*': length, '\'': length, ']': length, '}': length, '[': length,
                 '{': length, '\\': length}

    for i in range(len(substring) - 1):
        badvalues[substring[i]] = len(substring) - i - 1
        if substring[len(substring) - 1] not in badvalues.keys():
            # checking through keys
            badvalues[substring[len(substring) - 1]] = len(substring)
    return badvalues


def searchExact1(phrase, passage, badvalues):
    exactMatches = []
    passageIndex = 0
    lenpass = len(passage)
    lenphrase = len(phrase)
    badval = len(phrase)
    while passageIndex < lenpass - lenphrase:
        for i in range(lenphrase):
            # print("Comparing phrase[" + str(len(phrase)-1-i) + "] with passage[" + str(passageIndex + len(phrase)-1-i) + "]")
            if phrase[lenphrase - 1 - i] != passage[passageIndex + lenphrase - 1 - i]:
                badval = badvalues[passage[passageIndex + len(phrase) - 1 - i]]
                break
            if i == lenphrase - 1:
                # exactMatches.append("Exact: [" + str(passageIndex) + "]")
                return (passageIndex)
        passageIndex += badval
    return -1


# def searchExact2(phrase, passage, badvalues):
#     matches = {'m': [], 'i': [], 't': [], 's': [], 'd': []}
#     passageIndex = 0  # This is the first index of the first word in the current substring
#     badval = len(phrase)
#     lenpass = len(passage)
#     lenphrase = len(phrase)

#     while passageIndex < lenpass - lenphrase:
#         curflag = 'n'
#         firstMismatchFound = False
#         for i in range(lenphrase):
#             if curflag == 't':
#                 i += 1
#                 # print("Comparing phrase[" + str(len(phrase)-1-i) + "] with passage[" + str(passageIndex + len(phrase)-1-i) + "]")
#             localIndex = lenphrase - 1 - i
#             globalIndex = passageIndex + lenphrase - 1 - i
#             # pdb.set_trace()
#             if phrase[localIndex] != passage[globalIndex]:  # Is this part of the string a mismatch?
#                 if not firstMismatchFound:  # If there haven't been mismatches yet...
#                     badval = badvalues[passage[passageIndex + localIndex]]  # Save the badvalue
#                     # SUBSTITUTION is the default assumption.
#                     curflag = 's'
#                     # Check for TRANSPOSITION
#                     if phrase[localIndex] == passage[globalIndex - 1]:
#                         # . phrase:  name
#                         #  passage: nmae
#                         if phrase[localIndex - 1] == passage[globalIndex]:
#                             # A swap between two adjacent letters has occured.
#                             curflag = 't'
#                     elif phrase[localIndex] == passage[globalIndex + 1]:

#                       firstMismatchFound = True
#                     continue
#                 else:  # If there has already been a mismatch...
#                     curflag = 'n'
#                     break

#             if curflag != 'n' and i == lenphrase - 1:

#                 matches[curflag].append(" [" + str(passageIndex) + "]")
#                 badval = len(phrase) - 1
#         passageIndex += badval
#     return matches


def searchExact3(phrase, passage, badvalues):
    matches = {"INSERTIONS:": [], "TRANSPOSITIONS:": [], "SUBSTITUTIONS:": [], "DELETIONS:": []}
    passageIndex = 0  # This is the first index of the first word in the current substring
    lenpass = len(passage)
    lenphrase = len(phrase)

    while passageIndex <= lenpass - lenphrase:
        subPhrase = phrase
        traPhrase = phrase
        insPhrase = phrase
        delPhrase = phrase
        lastLetterIndex = passageIndex + lenphrase
        localIndex = 0
        globalIndex = 0
        for i in range(lenphrase):
            badval = lenphrase
            localIndex = lenphrase - 1 - i
            globalIndex = passageIndex + lenphrase - 1 - i
            if phrase[localIndex] != passage[globalIndex]:  # Is this part of the string a mismatch?
                badval = badvalues[passage[passageIndex + localIndex]]  # Save the badvalue
                insPhrase = phrase[:localIndex + 1] + passage[globalIndex] + phrase[localIndex + 1:]
                subPhrase = phrase[:localIndex] + passage[globalIndex] + phrase[localIndex + 1:]
                delPhrase = phrase[:localIndex] + phrase[localIndex + 1:]
                if lenphrase >= 2 and lenpass >= 2:
                    if localIndex == 0:
                        traPhrase = phrase[localIndex + 1] + phrase[localIndex]
                    else:
                        traPhrase = phrase[:localIndex - 1] + phrase[localIndex] + phrase[localIndex - 1] + phrase[
                                                                                                            localIndex + 1:]
                    # [1,2,3,4]
                else:
                    traPhrase = ""  # some result that would never match
                if insPhrase == passage[passageIndex - 1:lastLetterIndex]:
                    passageIndex -= 1
                    matches["INSERTIONS:"].append(insPhrase + ": [" + str(passageIndex) + "], ")
                if subPhrase == passage[passageIndex:lastLetterIndex]:
                    matches["SUBSTITUTIONS:"].append(subPhrase + ": [" + str(passageIndex) + "], ")
                if delPhrase == passage[passageIndex:lastLetterIndex - 1] or delPhrase == passage[
                                                                                          passageIndex + 1:lastLetterIndex]:
                    matches["DELETIONS:"].append(delPhrase + ": [" + str(passageIndex) + "], ")
                if traPhrase == passage[passageIndex:lastLetterIndex]:
                    matches["TRANSPOSITIONS:"].append(traPhrase + ": [" + str(passageIndex) + "], ")
                # pdb.set_trace()
                # passageIndex += badvalues[passage[passageIndex + localIndex]]
                break
        # pdb.set_trace()
        passageIndex += badvalues[passage[passageIndex + localIndex]]
        # pdb.set_trace()
    return matches

def fuzzySearch(phrase, passage, badvalues):
    tStart = time.time()
    exactmatches = searchExact1(phrase, passage, badvalues)
    tEnd = time.time()
    if exactmatches != -1:
        print(phrase + " found at Index [" + str(exactmatches) + "]")
        return tEnd - tStart

    tStart = time.time()
    # exactmatches = searchExact1(phrase, passage, badvalues)
    approxmatches = searchExact3(phrase, passage, badvalues)
    tEnd = time.time()
    # print(exactmatches)
    print(approxmatches)
    return tEnd - tStart


def getStringFromFile(filepath):
    returnString = ""
    myFile = open(filepath)
    # BUG: String not parsing correctly
    for line in myFile:
        for word in line.split():
            returnString += word + " "
    returnString[-1] == ""
    myFile.close()
    return returnString


def main():
    repeat = 'Y'
    while repeat:
        filepath = input("What file would you like to read from?")

        # CHECKING IF FILE EXISTS
        try:
            file = open(filepath)
        except FileNotFoundError as msg:
            print("Error: File not found.")
            print(str(msg))
            continue

        # CHECKING IF EXISTING FILE IS EMPTY
        filesize = os.path.getsize(filepath)
        if filesize == 0:
            print("The file is empty.")
            continue

        # SETTING APPROPRIATE VALUES
        passage = getStringFromFile(filepath)
        phrase = input("What word/phrase are you searching for?")
        badvalues = calcBadValues(phrase)
        time = fuzzySearch(phrase, passage, badvalues)
        print("Executed in " + str(time) + " seconds.")
        repeat = input("Would you like to search for another word? (Y/N)").lower()
    print("==========================------------------END OF PROGRAM------------------===========================")


if __name__ == "__main__":
    main()
