import React, { useEffect, useState } from 'react';
import IconButton from '@material-ui/core/IconButton';
import { FaAllergies } from 'react-icons/fa';
import { Button, Checkbox, Dialog, DialogActions, DialogContent, DialogContentText, FormControlLabel, FormGroup, makeStyles, TextField, Tooltip, Typography } from '@material-ui/core';
import apiCall from '../../utils/apiCall';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';


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
  const [availableAllergySymptoms, setAvailableAllergySymptoms] = useState([]);
  const [checkedValues, setCheckedValues] = useState({
    1: false,
    2: false,
    3: false,
    4: false,
    5: false,
    6: false,
    7: false,
  })

  // let checkboxList = []
  const checkboxList = {
    1: false,
    2: false,
    3: false,
    4: false,
    5: false,
    6: false,
    7: false,
  }

  useEffect(() => {
    apiCall('/api/allergicReaction/getAll', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(data => setAvailableAllergySymptoms(data))
    .catch(error => console.log(error));
  }, []);

  const editUserAllergySymptoms = () => {
    apiCall(`/api/user/addAllergicReaction?allergicReactionId=${checkedValues}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .catch(error => console.log(error));
  };

  const handleChange = (event) => {
    setCheckedValues({
      ...checkedValues, [event.target.name]: event.target.checked,
    });
    console.log(checkedValues)
  };

  const [open, setOpen] = useState(false);

  // const setCheckboxes = (items, array) => {
  //   items.map(item => {array.push(item.id[false])})
  //   console.log(checkboxList)
  // }


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
            <FormLabel component='legend'>Określ objawy jakich dziś doświadczyłeś/aś.</FormLabel>
            <FormGroup>
              {availableAllergySymptoms.map(checkboxItem =>
                <FormControlLabel
                  control={<Checkbox checked={checkedValues} onChange={handleChange} name={checkboxItem.id} />}
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