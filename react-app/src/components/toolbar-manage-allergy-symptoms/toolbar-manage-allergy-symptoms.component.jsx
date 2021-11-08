import React, { useEffect, useState } from 'react';
import apiCall from '../../utils/apiCall';
import IconButton from '@material-ui/core/IconButton';
import { FaAllergies } from 'react-icons/fa';
import { Button, Checkbox, Dialog, DialogActions, DialogContent, DialogContentText, FormControlLabel, FormGroup, makeStyles, Tooltip } from '@material-ui/core';
import FormControl from '@material-ui/core/FormControl';


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

  // function to get checkboxes with all allergies (unchecked) + user's allergies (checked)
  const prepareCheckboxes = (allSymptoms, userSymptoms) => {
    let checkboxes = {};
    allSymptoms.forEach(obj => {
      checkboxes[obj.id] = false;
    });
    userSymptoms.forEach(obj => {
      checkboxes[obj.id] = true;
    });
    return checkboxes;
  };

  const [allAllergySymptoms, setAllAllergySymptoms] = useState([]);
  const [userAllergySymptoms, setUserAllergySymptoms] = useState([]);
  const [checkboxes, setCheckboxes] = useState({});
  useEffect(() => setCheckboxes(prepareCheckboxes(Array.from(allAllergySymptoms), Array.from(userAllergySymptoms))),
    [allAllergySymptoms, userAllergySymptoms]);

  //TODO: Refactor the endpoint names
  useEffect(() => {
    apiCall('/api/allergicReaction/getAll', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(data => setAllAllergySymptoms(data))
    .catch(error => console.log(error));

    apiCall('/api/user/getUserAllergicReaction', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(data => setUserAllergySymptoms(data))
    .catch(error => console.log(error));
  }, []);

  const editUserAllergySymptoms = () => {
    const symptomIds = Object.keys(checkboxes);
    const userSymptomIds = symptomIds.filter((symptomId) => (checkboxes[symptomId])).map(Number);

    apiCall('/api/user/editAllergySymptoms', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        symptomIds: userSymptomIds,
      }),
    })
    .catch(error => console.log(error));
  };

  const handleChange = (props) => (event) => {
    setCheckboxes({ ...checkboxes, [props]: event.target.checked });
  };

  const [open, setOpen] = useState(false);

  const openAllergySymptomsDialog = () => {
    setOpen(true);
  };

  const closeAllergySymptomsDialog = () => {
    setOpen(false);
  };

  return (
    <>
      <Tooltip title='Dodaj objawy alergii' className={icon}>
        <IconButton onClick={openAllergySymptomsDialog}>
          <FaAllergies />
        </IconButton>
      </Tooltip>

      {/*hidden modification dialog*/}
      <Dialog open={open} onClose={closeAllergySymptomsDialog}>
        <DialogContent>
          <DialogContentText>
            Edytuj objawy alergiczne
          </DialogContentText>
          <FormControl sx={{ m: 3 }} component='fieldset' variant='standard'>
            <FormGroup>
              {allAllergySymptoms.map(checkboxItem =>
                <FormControlLabel
                  control={<Checkbox checked={checkboxes[checkboxItem.id]} onChange={handleChange(checkboxItem.id)} />}
                  label={checkboxItem.name}
                />)}
            </FormGroup>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={closeAllergySymptomsDialog}>Anuluj</Button>
          <Button onClick={editUserAllergySymptoms}>Zapisz</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default ToolbarManageAllergySymptoms;