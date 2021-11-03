import React from 'react';
import IconButton from '@material-ui/core/IconButton';
import { BiNote } from 'react-icons/bi';
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

const ToolbarManageNote = () => {
  const { icon } = useStyles();

  return (
    <Tooltip title='Edytuj notatkę' className={icon}>
      <IconButton>
        <BiNote />
      </IconButton>
    </Tooltip>
  );
};

export default ToolbarManageNote;