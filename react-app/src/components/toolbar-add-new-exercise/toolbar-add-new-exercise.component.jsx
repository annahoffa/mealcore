import React from 'react';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import { makeStyles, Tooltip } from '@material-ui/core';


const useStyles = makeStyles(() => ({
    icon: {
      maxWidth: 'max-content',
      maxHeight: 'fit-content',
      backgroundColor: '#F6F6F6',
      borderRadius: '5px',
    },
  }
));

const ToolbarAddNewExercise = () => {
  const { icon } = useStyles();

  return (
    <Tooltip title='Dodaj aktywność' className={icon}>
      <IconButton>
        <AddIcon />
      </IconButton>
    </Tooltip>
  );
};

export default ToolbarAddNewExercise;