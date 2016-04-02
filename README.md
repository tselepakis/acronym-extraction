# acronym-extraction
The aim of this project is the detection of acronyms and their possible expansions in medical texts. The proposed method also applies in texts from different domains.

The algorithm:

1.	Represent the text as an array of tokens.

2.	Define an acronym pattern, with Java regular expressions.

3.	Iterate over the array, if a token matches the pattern defined earlier, invoke the <i>searchPossibleExpansion()</i> method.

The <i>searchPossibleExpantion()</i> method will search in a window before and after the possible acronym to find the best expansion. After experimentation we set the window size to three plus the length of the acronym.

<p align="center">
<i>window = 3 + acronymLength</i>
</p>

The size of the window is in terms of tokens, for example if window = 5, we will search five tokens before and five tokens after acronyms position.

The search process is trying to match the characters of the acronym, with the initial letters of the tokens belonging in the window. This approach produces more than one possible expansion. So, we are proposing a scoring function to help as order the outcomes and choose the best.

The scoring function associates a score in every expansion, representing our confidence that it is correct. Initially score is 0, for every acronym’s character that we match with a word, we add 0.5, plus 2 multiplied by the invert of the word’s distance from the acronym.
######Scoring function:
<p align="center">
<i>score = score + 0.5 + ( 2 / |acronymPosition-wordPosition| )</i>
</p>

Thereby, from all possible expansions produced for an acronym we prefer those which have more matches with acronym’s characters and are closer to it.
