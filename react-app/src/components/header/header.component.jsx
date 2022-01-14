import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../../appContext/providers';
import { AppBar, Toolbar, Button, makeStyles, IconButton, Popover } from '@material-ui/core';
// import clsx from 'classnames';
// import { ReactComponent as Logo } from '../../assets/logo_horizontal.svg';
// import { Menu as MenuIcon } from '@material-ui/icons';
// import Drawer from '../drawer/drawer.component';
// import LogOutPage from '../../pages/log-out/logOutPage';

import { HEADER_DATA_UNAUTHENTICATED } from './headerUnauthenticated.data.js';
import { HEADER_DATA_AUTHENTICATED } from './headerAuthenticated.data.js';
import { HEADER_DATA_ADMIN } from './headerAdmin.data.js';

import './header.styles.scss';


const useStyles = makeStyles(() => ({
  header: {
    backgroundColor: 'rgba(255, 255, 255, 0.8)', // #FFFFFF with 0.8 opacity on this element only
    padding: '0 30px',
  },
  toolbar: {
    display: 'flex',
    justifyContent: 'space-between',
  },
  logoContainer: {
    fontSize: '1rem',
    color: '#20C34E',
    height: '100%',
    width: '10%',
    textDecoration: 'none',
  },
  menuButton: {
    marginLeft: '2rem',
  },
  accountButton: {
    marginLeft: '2rem',
    '&:hover': {},
  },
}));

const Header = () => {
  const authContext = useContext(AuthContext);
  let headerData;
  if(authContext.isAdmin) {
    headerData = HEADER_DATA_ADMIN;
  } else if(authContext.isLoggedIn) {
    headerData = HEADER_DATA_AUTHENTICATED;
  } else headerData = HEADER_DATA_UNAUTHENTICATED;

  const [anchorEl, setAnchorEl] = React.useState(null);
  const isPopoverOpen = Boolean(anchorEl);

  const handleTogglePopover = (event) => {
    if(anchorEl) {
      setAnchorEl(null);
    } else {
      setAnchorEl(event.currentTarget);
    }
  };

  const handlePopoverClose = () => {
    setAnchorEl(null);
  };

  const { header, toolbar, logoContainer, menuButton, accountButton } = useStyles();

  const displayLogo = () => (
    <Link className={logoContainer} to='/'>
      {/*<Logo />*/}
      <span>MealCore</span>
    </Link>
  );

  const getMenuButtons = (headerData) => {
    return headerData.map(({ label, href, variant, color, icon }) => (
      !icon ? (
        <Button
          {...{
            key: label,
            variant: variant || 'text',
            color: color || 'inherit',
            className: menuButton,
            to: href,
            component: Link,
          }}
        >
          {label}
        </Button>
      ) : (
        <IconButton
          onClick={handleTogglePopover}
          className={accountButton}
          aria-describedby='mouse-over-popover'
        >
          {icon}
          <Popover
            id='mouse-over-popover'
            open={isPopoverOpen}
            anchorEl={anchorEl}
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'center',
            }}
            transformOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            onClose={handlePopoverClose}
            elevation={4}
            disableRestoreFocus={false}
          >
            <Button to='/myaccount' component={Link}>
              Ustawienia
            </Button>
            <Button to='/logout' component={Link}>
              Wyloguj
            </Button>
          </Popover>
        </IconButton>
      )
    ));
  };

  return (
    <AppBar position='fixed' elevation='0' className={header}>
      <Toolbar className={toolbar}>
        {displayLogo()}
        <div>{getMenuButtons(headerData)}</div>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
