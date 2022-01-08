import React from 'react';
import { LinearProgress, makeStyles } from '@material-ui/core';


// For some reason nothing else works
const useStyles = makeStyles(() => ({
  root: {
    height: '1.5em',
    borderRadius: 3,
  },
  overflowingValue: {
    backgroundColor: '#ec5353',
  },
  exactValue: {
    backgroundColor: '#fab041',
  },
  optimalValue1: {
    backgroundColor: '#3ec443',
  },
  optimalValue2: {
    backgroundColor: '#b0e3ae',
  },
}));

const normalise = (value, maxValue) => {
  const MIN_VALUE = 0;
  return (((value - MIN_VALUE) * 100) / (maxValue - MIN_VALUE));
};

const PercentageBar = ({ value, maxValue }) => {
  const classes = useStyles();

  if(parseFloat(value) > parseFloat(maxValue)) {
    return (
      <LinearProgress
        variant='determinate'
        value={100}
        classes={{ barColorPrimary: classes.overflowingValue }}
        className={classes.root}
      />);
  } else if(value === maxValue) {
    return (
      <LinearProgress
        variant='determinate'
        value={100}
        classes={{ barColorPrimary: classes.exactValue }}
        className={classes.root}
      />);
  } else {
    return (
      <LinearProgress
        variant='determinate'
        value={normalise(value, maxValue)}
        classes={{ barColorPrimary: classes.optimalValue1, colorPrimary: classes.optimalValue2 }}
        className={classes.root}
      />);
  }
};

export default PercentageBar;