import React from 'react';

import './show-personal-data.styles.scss';


const ShowPersonalData = ({ items }) => {

  const getGender = gender => {
    switch(gender) {
      case 'FEMALE':
        return 'Kobieta';
      case 'MALE':
        return 'Mężczyzna';
      default:
        return 'Nie podano.';
    }
  };

  const getActivityLevel = activityLevel => {
    switch(activityLevel) {
      case 'LACK_OF_ACTIVITY':
        return 'Siedzący tryb życia';
      case 'LITTLE_ACTIVITY':
        return 'Niska aktywność';
      case 'MODERATE_ACTIVITY':
        return 'Średnia aktywność';
      case 'LOT_OF_ACTIVITY':
        return 'Wysoka aktywność';
      case 'VERY_ACTIVE':
        return 'Intensywna aktywność';
    }
  };

  return (
    <div className='personal-data-container'>
      <div className='item'>
        <span><b>Email:</b></span>
        <span>{items.login}</span>
      </div>
      <div className='item'>
        <span><b>Płeć:</b></span>
        <span>{getGender(items.gender)}</span>
      </div>
      <div className='item'>
        <span><b>Wiek:</b></span>
        <span>{items.age}</span>
      </div>
      <div className='item'>
        <span><b>Wzrost:</b></span>
        <span>{items.height}</span>
      </div>
      <div className='item'>
        <span><b>Waga:</b></span>
        <span>{items.weight}</span>
      </div>
      <div className='item'>
        <span><b>Poziom aktywności:</b></span>
        <span>{getActivityLevel(items.activityLevel)}</span>
      </div>
      <div className='item'>
        <span><b>Alergeny:</b></span>
        <span>{items.allergens.map(item => (<p>{item}</p>))}</span>
      </div>
    </div>
  );
};

export default ShowPersonalData;
