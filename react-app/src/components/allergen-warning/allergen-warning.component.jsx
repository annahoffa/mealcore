import React, { useState } from 'react';
import { Popover, Typography, makeStyles } from '@material-ui/core';
import WarningIcon from '@material-ui/icons/Warning';

import './allergen-warning.styles.scss';


const useStyles = makeStyles(() => ({
  popover: {
    pointerEvents: 'none',
    '& > *': {
      padding: '1rem',
    },
  },
}));

const AllergenWarning = () => {
  const [anchorEl, setAnchorEl] = useState(null);
  const isPopoverOpen = Boolean(anchorEl);

  const handlePopoverOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handlePopoverClose = () => {
    setAnchorEl(null);
  };

  const { popover } = useStyles();

  return (
    <div>
      <WarningIcon
        onMouseEnter={handlePopoverOpen}
        onMouseLeave={handlePopoverClose}
        aria-haspopup='true'
        aria-describedby='allergen-warning-popover'
      />
      <Popover
        id='allergen-warning-popover'
        className={popover}
        open={isPopoverOpen}
        anchorEl={anchorEl}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
        transformOrigin={{
          vertical: 'center',
          horizontal: 'right',
        }}
        onClose={handlePopoverClose}
        disableRestoreFocus
      >
        <Typography>Ten produkt zawiera zdefiniowane przez Ciebie alergeny!</Typography>
      </Popover>
    </div>
  );
};

export default AllergenWarning;
