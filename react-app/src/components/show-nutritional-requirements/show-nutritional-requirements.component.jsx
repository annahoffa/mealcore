import React from 'react';
import PercentageBar from '../percentage-bar';

import './show-nutritional-requirements.styles.scss';


const ShowNutritionalRequirements = ({ maxValues, currentValues }) => {
  return (
    <div className='daily-requirements-container'>
      <div className='item'>
        <span><b>Kcal:</b></span>
        <div className='percentage-bar'>
          <span className='numbers'>{currentValues?.kcal || '\u2014'} / {parseFloat(maxValues?.kcal).toFixed(2)}</span>
          <PercentageBar value={currentValues?.kcal} maxValue={maxValues?.kcal} />
        </div>
      </div>
      <div className='item'>
        <span><b>Białka:</b></span>
        <div className='percentage-bar'>
          <span
            className='numbers'>{currentValues?.proteins || '\u2014'} / {parseFloat(maxValues?.proteins).toFixed(2)}</span>
          <PercentageBar value={currentValues?.proteins} maxValue={maxValues?.proteins} />
        </div>
      </div>
      <div className='item'>
        <span><b>Węglowodany:</b></span>
        <div className='percentage-bar'>
          <span
            className='numbers'>{currentValues?.carbohydrates || '\u2014'} / {parseFloat(maxValues?.carbohydrates).toFixed(2)}</span>
          <PercentageBar value={currentValues?.carbohydrates} maxValue={maxValues?.carbohydrates} />
        </div>
      </div>
      <div className='item'>
        <span><b>Tłuszcze:</b></span>
        <div className='percentage-bar'>
          <span className='numbers'>{currentValues?.fat || '\u2014'} / {parseFloat(maxValues?.fat).toFixed(2)}</span>
          <PercentageBar value={currentValues?.fat} maxValue={maxValues?.fat} />
        </div>
      </div>
      <div className='item'>
        <span><b>Błonnik:</b></span>
        <div className='percentage-bar'>
          <span
            className='numbers'>{currentValues?.fiber || '\u2014'} / {parseFloat(maxValues?.fiber).toFixed(2)}</span>
          <PercentageBar value={currentValues?.fiber} maxValue={maxValues?.fiber} />
        </div>
      </div>
    </div>
  );
};

export default ShowNutritionalRequirements;
