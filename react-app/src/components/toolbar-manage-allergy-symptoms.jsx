import React, { useEffect, useState } from 'react';
import apiCall from '../utils/apiCall';
import IconButton from '@material-ui/core/IconButton';
import { FaAllergies } from 'react-icons/fa';
import { Button, Checkbox, Dialog, DialogActions, DialogContent, DialogContentText, FormControl, FormControlLabel, FormGroup, makeStyles, Tooltip } from '@material-ui/core';
import { useQuery } from 'react-query';


const useStyles = makeStyles(() => ({
    icon: {
      maxWidth: 'max-content',
      maxHeight: 'fit-content',
      backgroundColor: '#F6F6F6',
      borderRadius: '5px',
    },
  }
));

const ToolbarManageAllergySymptoms = ({ date }) => {
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

  const [checkboxes, setCheckboxes] = useState({});

  const allergicReactionQuery = useQuery(['allergicReaction'],
    () => apiCall('/api/allergicReaction/getAll', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }));

  const userAllergicReactionQuery = useQuery(['userAllergicReaction', date],
    (context) => (
      apiCall(`/api/user/getUserAllergicReaction?date=${context.queryKey[1]}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })),
    {
      onSuccess: (data) => {
        const newCheckboxes = {};
        data.map(reaction => reaction.id).forEach(id => newCheckboxes[id] = true);
        setCheckboxes(newCheckboxes);
      },
    },
  );

  const closeAllergySymptomsDialog = () => {
    setOpen(false);
    setCheckboxes({});
  };

  const editUserAllergySymptoms = async() => {
    const symptomIds = Object.keys(checkboxes);
    const userSymptomIds = symptomIds.filter((symptomId) => (checkboxes[symptomId])).map(Number);

    await apiCall(`/api/user/editAllergySymptoms`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        date: date,
        symptomIds: userSymptomIds,
      }),
    })
    .catch(error => console.log(error));

    closeAllergySymptomsDialog();
    await userAllergicReactionQuery.refetch();
  };

  const handleChange = (props) => (event) => {
    setCheckboxes({ ...checkboxes, [props]: event.target.checked });
  };

  const [open, setOpen] = useState(false);

  const openAllergySymptomsDialog = () => {
    setOpen(true);
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
              {allergicReactionQuery.data?.map(checkboxItem =>
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