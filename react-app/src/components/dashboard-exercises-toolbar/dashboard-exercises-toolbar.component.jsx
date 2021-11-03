import React from 'react';
import ToolbarAddNewExercise from '../toolbar-add-new-exercise/toolbar-add-new-exercise.component';
import { makeStyles } from '@material-ui/core';


const useStyles = makeStyles(() => ({
  toolbar: {
    '&>*': {
      marginRight: '0.2em',
    },
  },
}));

const DashboardExercisesToolbar = () => {
  const { toolbar } = useStyles();

  return (
    <div className={toolbar}>
      <ToolbarAddNewExercise />
    </div>
  );
};

export default DashboardExercisesToolbar;