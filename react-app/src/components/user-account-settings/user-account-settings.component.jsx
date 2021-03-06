import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../appContext/providers';
import apiCall from '../../utils/apiCall';
import { Typography } from '@material-ui/core';
import Skeleton from '@material-ui/lab/Skeleton';
import SettingsModalDialog from '../settings-modal-dialog/settings-modal-dialog.component';
import ChangeEmailForm from '../change-email-form/change-email-form.component';
import ChangePasswordForm from '../change-password-form/change-password-form.component';
import ShowPersonalData from '../show-personal-data/show-personal-data.component';
import ChangePersonalDataForm from '../change-personal-data/change-personal-data.component';

import './user-account-settings.styles.scss';


const UserAccountSettings = () => {
  const authContext = useContext(AuthContext);

  const [personalData, setPersonalData] = useState();

  const getPersonalData = () => {
    if(authContext.isLoggedIn) {
      apiCall('/api/user/getPersonalData', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(data => setPersonalData(data))
      .catch(error => console.log(error));
    }
  };

  useEffect(() => {
    getPersonalData();
  }, [personalData]);


  const [allDialogs, setDialogs] = useState({
    avatarDialog: false,
    emailDialog: false,
    passwordDialog: false,
    personalDataDialog: false,
  });

  const handleClickOpen = (dialog) => {
    setDialogs({ ...allDialogs, [dialog]: true });
  };

  const handleClose = (dialog) => {
    setDialogs({ ...allDialogs, [dialog]: false });
  };


  return (
    <div className='container'>

      {/*TODO add tab name here*/}
      <Typography variant='h5' align='center'>
        Ustawienia konta
      </Typography>
      <br />

      <div className='avatar-container'>
        <div className='avatar-placeholder' />
        <div className='settings-element'>
          <SettingsModalDialog
            dialogName='avatarDialog'
            openStatus={allDialogs.avatarDialog}
            handleClickOpen={handleClickOpen}
            handleClose={handleClose}
            ariaLabelText='change-avatar-dialog'
            dialogTitle='Zmie?? sw??j avatar'
            buttonName='Zmie?? avatar'
          />
        </div>
      </div>

      <div className='text-settings'>
        <div className='settings-element'>
          {/*<div className='icon-text-clip'>*/}
          {/*  <EmailIcon />*/}
          {/*</div>*/}
          <div className='modal-button'>
            <SettingsModalDialog
              dialogName='emailDialog'
              openStatus={allDialogs.emailDialog}
              handleClickOpen={handleClickOpen}
              handleClose={handleClose}
              ariaLabelText='change-email-dialog'
              dialogTitle='Zmie?? sw??j adres e-mail'
              buttonName='Zmie?? adres e-mail'
              form={<ChangeEmailForm />}
            />
          </div>
        </div>
        <div className='settings-element'>
          {/*<div className='icon-text-clip'>*/}
          {/*  <LockIcon />*/}
          {/*</div>*/}
          <div className='modal-button'>
            <SettingsModalDialog
              dialogName='passwordDialog'
              openStatus={allDialogs.passwordDialog}
              handleClickOpen={handleClickOpen}
              handleClose={handleClose}
              ariaLabelText='change-password-dialog'
              dialogTitle='Zmie?? swoje has??o'
              buttonName='Zmie?? has??o'
              form={<ChangePasswordForm />}
            />
          </div>
        </div>
      </div>

      <div>
        {personalData === undefined ? <Skeleton /> : <ShowPersonalData items={personalData} />}
        <SettingsModalDialog
          dialogName='personalDataDialog'
          openStatus={allDialogs.personalDataDialog}
          handleClickOpen={handleClickOpen}
          handleClose={handleClose}
          ariaLabelText='change-personal-data-dialog'
          dialogTitle='Zmie?? swoje parametry'
          buttonName='Zmie?? swoje parametry'
          form={<ChangePersonalDataForm currentData={personalData} />}
        />
      </div>
    </div>
  );
};

export default UserAccountSettings;