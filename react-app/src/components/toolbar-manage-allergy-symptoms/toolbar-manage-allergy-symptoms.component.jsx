import React from 'react';
import IconButton from '@material-ui/core/IconButton';
import { MdWaterDrop } from 'react-icons/md';
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

const ToolbarManageAllergySymptoms = () => {
  const { icon } = useStyles();

  return (
    <Tooltip title='Dodaj objawy alergii' className={icon}>
      <IconButton>
        <MdWaterDrop />
      </IconButton>
    </Tooltip>
  );
};

export default ToolbarManageAllergySymptoms;