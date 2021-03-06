import React from 'react';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';


export const HEADER_DATA_AUTHENTICATED = [
  {
    label: 'Panel użytkownika',
    href: '/dashboard',
  },
  {
    label: 'Raport produktów problematycznych',
    href: '/summary'
  },
  {
    label: 'Kalkulator BMI',
    href: '/bmicalculator',
  },
  {
    label: 'Wyszukiwarka produktów',
    href: '/productsearch',
  }, //TODO: Make /suggestproduct into a button somewhere inside the product search, not a link from a header
  {
    label: 'Dodaj produkt',
    href: '/suggestproduct',
  },
  {
    icon: <AccountCircleIcon />,
    label: 'Konto',
    href: '',
  },
];

export const DRAWER_DATA_AUTHENTICATED = [
  {
    label: 'Panel użytkownika',
    href: '/dashboard',
  },
  {
    label: 'Kalkulator BMI',
    href: '/bmicalculator',
  },
  {
    label: 'Wyszukiwarka produktów',
    href: '/productsearch',
  },
  {
    label: 'Dodaj produkt',
    href: '/suggestproduct',
  },
  {
    label: 'Ustawienia konta',
    href: '/myaccount',
  },
  {
    label: 'Wyloguj',
    href: '/logout',
  },
];
