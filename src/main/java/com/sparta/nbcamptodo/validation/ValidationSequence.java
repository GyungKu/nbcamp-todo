package com.sparta.nbcamptodo.validation;

import jakarta.validation.GroupSequence;

import static com.sparta.nbcamptodo.validation.ValidationGroups.*;

@GroupSequence({SizeGroup.class, PatternGroup.class})
public interface ValidationSequence {
}
