import React from 'react';

import './show-nutritional-requirements.styles.scss'

const ShowNutritionalRequirements = ({ maxValues, currentValues }) => {
  return (
    <div className='daily-requirements-container'>
      <div className='item'>
        <span><b>Kcal:</b></span>
        <span>{currentValues?.kcal || '\u2014'} / {parseFloat(maxValues.kcal).toFixed(2)}</span>
      </div>
      <div className='item'>
        <span><b>Białka:</b></span>
        <span>{currentValues?.proteins || '\u2014'} / {parseFloat(maxValues.proteins).toFixed(2)}</span>
      </div>
      <div className='item'>
        <span><b>Węglowodany:</b></span>
        <span>{currentValues?.carbohydrates || '\u2014'} / {parseFloat(maxValues.carbohydrates).toFixed(2)}</span>
      </div>
      <div className='item'>
        <span><b>Tłuszcze:</b></span>
        <span>{currentValues?.fats || '\u2014'} / {parseFloat(maxValues.fat).toFixed(2)}</span>
      </div>
      <div className='item'>
        <span><b>Błonnik:</b></span>
        <span>{currentValues?.fiber || '\u2014'} / {parseFloat(maxValues.fiber).toFixed(2)}</span>
      </div>
    </div>
  );
};

export default ShowNutritionalRequirements;
