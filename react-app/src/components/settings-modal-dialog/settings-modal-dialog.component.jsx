import React from 'react';

import {Box, Button, Dialog, DialogContent, DialogContentText, DialogTitle} from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';

import './settings-modal-dialog.styles.scss';


const SettingsModalDialog = (props) => {

  return (
    <>
      <Button variant='outlined' onClick={() => props.handleClickOpen(props.dialogName)} fullWidth>{props.buttonName}</Button>
      <Dialog open={props.openStatus} disableBackdropClick aria-labelledby={props.ariaLabelText}>
        <DialogTitle id={props.ariaLabelText}>
          <Box display='flex' alignItems='center'>
            <Box flexGrow={1}>{props.dialogTitle}</Box>
            <Box>
              <IconButton onClick={() => props.handleClose(props.dialogName)}>
                <CloseIcon />
              </IconButton>
            </Box>
          </Box>
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
          </DialogContentText>
          {props.form ? props.form : 'Ta operacja w tym momencie nie jest możliwa do wykonania.'}
        </DialogContent>
      </Dialog>
    </>
  );

};

export default SettingsModalDialog;
