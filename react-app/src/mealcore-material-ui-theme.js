import { createMuiTheme } from '@material-ui/core';


export const theme = createMuiTheme({
  palette: {
    primary: {
      main: '#20C34E',
    },
    secondary: {
      main: '#C1EC46',
    },
  },
  typography: {
    fontFamily: [
      'Poppins',
      'sans-serif',
    ].join(','),
    h2: {
      fontWeight: 700,
      fontSize: '4.2rem',
    },
    h4: {
      fontWeight: 600,
    },
    subtitle2: {
      fontWeight: 600,
      fontSize: '1.2rem',
    },
    body2: {
      fontWeight: 400,
    },
    overline: {
      fontWeight: 700,
      letterSpacing: '0.5em',
    },
  },
});

theme.overrides = {
  /* Custom buttons */
  MuiButton: {
    root: {
      textTransform: 'none', // removes uppercase transformation
      fontFamily: theme.typography.fontFamily,
    },
    text: {
      backgroundColor: 'transparent',
      border: 'none',
      fontWeight: 400,
    },
    outlinedPrimary: {
      backgroundColor: 'transparent',
      //boxShadow: 'inset 0px 0px 0px 3px', // an inside border
      border: '2px solid',
      borderColor: theme.palette.primary.main,
      color: theme.palette.primary.main,
      fontWeight: 500,
      '&:hover': {
        border: '2px solid',
        borderColor: theme.palette.primary.main,
      },
      '&:active': {
        border: '2px solid',
        borderColor: theme.palette.primary.main,
      },
    },
    containedPrimary: {
      padding: '7px 17px', // matches the outlined ver.
      backgroundColor: theme.palette.primary.main,
      color: theme.palette.primary.contrastText,
      fontWeight: 500,
      '&:hover': {
        backgroundColor: theme.palette.primary.main,
        color: theme.palette.primary.contrastText,
      },
      '&:active': {
        backgroundColor: theme.palette.secondary.main,
        color: theme.palette.primary.contrastText,
      },
    },
  },
};

theme.props = {
  MuiButton: {
    disableElevation: true,
  },
};
