import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';
import { Typography } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle';
import EditIcon from '@material-ui/icons/Edit';
import DefineExerciseDuration from '../define-exercise-duration/define-exercise-duration.component';

import './dashboard-exercise.styles.scss';


const DashboardExercise = ({ item: exercise }) => {

  const modifyExerciseDuration = () => {
    apiCall(`/api/user/editExercise?sportId=${exercise.id}&duration=${exerciseDuration}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(() => {
      window.location.reload();
    })
    .catch(error => console.log(error));

    closeDurationDialog();
  };

  const deleteExerciseFromDashboard = () => {
    apiCall(`/api/user/removeExercise?sportId=${exercise.id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(() => {
      window.location.reload();
    })
    .catch(error => console.log(error));
  };

  //--- Exercise duration modification dialog ---

  const [exerciseDuration, setExerciseDuration] = useState('');

  const handleChange = (event) => {
    setExerciseDuration(event.target.value);
  };

  const [open, setOpen] = useState(false);

  const openDurationDialog = () => {
    setOpen(true);
  };

  const closeDurationDialog = () => {
    setOpen(false);
  };

  const durationProps = {
    exerciseDuration: exerciseDuration,
    setExerciseDuration: setExerciseDuration,
    open,
    openQuantityDialog: openDurationDialog,
    closeQuantityDialog: closeDurationDialog,
    handleChange,
  };
  //------------------------------------

  return (
    <div className='dashboard-exercise'>
      <div className='exercise-title'>
        <Typography noWrap variant='body1'><b>{exercise.name}</b></Typography>
        <div className='icons'>
          <IconButton onClick={openDurationDialog} title='Zmień czas trwania'
            aria-label="Modify exercise's duration time">
            <EditIcon />
          </IconButton>

          {/*hidden modification dialog*/}
          <DefineExerciseDuration durationProps={durationProps} apiCall={modifyExerciseDuration} />

          <IconButton onClick={deleteExerciseFromDashboard} title='Usuń aktywność'
            aria-label='Delete exercise from dashboard'>
            <RemoveCircleIcon />
          </IconButton>
        </div>
      </div>
      <div className='exercise-details'>
        <span>{exercise.kcalPerHour || '\u003F'} kcal/h &#215; {exercise.duration || '\u003F'} h</span>
        <span><b>Łącznie spalonych:</b> {exercise.calculatedKcal || '\u003F'} kcal</span>
      </div>
    </div>
  );
};

export default DashboardExercise;
