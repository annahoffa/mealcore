import React, { useEffect, useState } from 'react';
import apiCall from '../utils/apiCall';
import { Button, Container, Dialog, DialogActions, DialogContent, DialogContentText, makeStyles, TextField, Tooltip, Typography } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import { BiNote } from 'react-icons/bi';


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
  const [noteContents, setNoteContents] = useState('');

  useEffect(() => {
    apiCall('/api/user/getNote', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(data => setNoteContents(data.text))
    .catch(error => console.log(error));
  }, []);

  const editNote = () => {
    apiCall(`/api/user/editNote?note=${noteContents}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .catch(error => console.log(error));
    closeNoteDialog();
  };

  const handleChange = (event) => {
    setNoteContents(event.target.value);
  };

  const [open, setOpen] = useState(false);

  const openNoteDialog = () => {
    setOpen(true);
  };

  const closeNoteDialog = () => {
    setOpen(false);
  };

  return (
    <>
      <Tooltip title='Edytuj notatkę' className={icon}>
        <IconButton onClick={openNoteDialog}>
          <BiNote />
        </IconButton>
      </Tooltip>

      {/*hidden modification dialog*/}
      <Dialog open={open} onClose={closeNoteDialog}>
        <DialogContent>
          <DialogContentText>
            Edytuj notatkę
          </DialogContentText>
          <Container>
            <Typography variant='body2' align='center'>Tu możesz zapisać swoje własne uwagi na dany dzień.</Typography>
            <br />
            <TextField
              multiline
              rows={5}
              label='Twoja notatka'
              value={noteContents}
              onChange={handleChange}
              placeholder='Tu zacznij pisać...'
              variant='outlined'
              fullWidth
            />
          </Container>
        </DialogContent>
        <DialogActions>
          <Button onClick={closeNoteDialog}>Anuluj</Button>
          <Button onClick={editNote}>Zapisz</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default ToolbarManageNote;