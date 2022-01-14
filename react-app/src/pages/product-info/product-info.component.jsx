import React, { useEffect, useState, useContext } from 'react';
import { useHistory } from 'react-router-dom';
import apiCall from '../../utils/apiCall';
import { AuthContext } from '../../appContext/providers';
import { Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Tooltip, Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import CircularProgress from '@mui/material/CircularProgress';
import MainContent from '../../components/main-content/main-content.component';
import AllergenWarning from '../../components/allergen-warning/allergen-warning.component';
import DefineProductQuantity from '../../components/define-product-quantity/define-product-quantity.component';
import AutoFitImage from 'react-image-autofit-frame';

import './product-info.styles.scss';


const ProductInfoPage = (props) => {
    const productId = props.match.params.id; // query extracted from the browser's url field
    let history = useHistory();
    const [state, setState] = useState();
    const authContext = useContext(AuthContext);

    useEffect(() => {
      apiCall(`/api/products/findById?productId=${productId}`)
      .then(data => setState(data))
      .catch(error => console.log(error));
    }, []);

    const sendProductToDashboard = () => {
      apiCall(`/api/user/addProduct?productId=${productId}&quantity=${productQuantity}&category=${productCategory || 'OTHER'}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(() => {
        closeQuantityDialog();
        history.push({ pathname: '/dashboard' });
      })
      .catch(error => console.log(error));
      console.log(productCategory);
    };

    //--- Quantity modification dialog ---

    const dishCategories = [
      { id: 0, label: 'Śniadanie', query: 'BREAKFAST' },
      { id: 1, label: 'Lunch', query: 'LUNCH' },
      { id: 2, label: 'Obiad', query: 'DINNER' },
      { id: 3, label: 'Kolacja', query: 'SUPPER' },
      { id: 4, label: 'Przekąska', query: 'SNACK' },
      { id: 9, label: 'Inne', query: 'OTHER' },
    ];

    const [productQuantity, setProductQuantity] = useState('');
    const [productCategory, setProductCategory] = useState(dishCategories[6]);

    const handleChange = (event) => {
      setProductQuantity(event.target.value);
    };

    const [open, setOpen] = useState(false);

    const openQuantityDialog = () => {
      setOpen(true);
    };

    const closeQuantityDialog = () => {
      setOpen(false);
    };

    const quantityProps = {
      productQuantity,
      setProductQuantity,
      dishCategories,
      productCategory,
      setProductCategory,
      open,
      openQuantityDialog,
      closeQuantityDialog,
      handleChange,
    };
    //------------------------------------

    const getIngredients = (ingredients) => {
      return ingredients ? ingredients.ingredientsText : 'Brak danych';
    };

    const getAdditives = (ingredients) => {
      const additives = ingredients?.additives;

      if(!additives || additives.length === 0) {
        return 'Brak dodatków';
      }

      return additives.map((item, key) => {
        return (
          <Tooltip key={key} title={item.description}>
            <span>{item.name}</span>
          </Tooltip>
        );
      });
    };


    const getNutrients = (nutrients) => {
      //const isNutrientsEmpty = true;

      function formatNutrients(data) {
        if(data.size < 1)
          return data;
        else
          return (data.charAt(0).toUpperCase() + data.slice(1)).replace('_', ' ');
      }

      function isEmptyNutrients(data) {
        for(const key of Object.keys(data))
          if(isNotEmpty(key))
            return false;
        return true;
      }

      function isNotEmpty(key) {
        return key !== 'id' && key !== 'productId' && nutrients[key];
      }

      return (
        <TableContainer component={Paper}>
          <Table size='small' className='product-table'>
            <TableHead>
              <TableRow style={{ backgroundColor: '#e4e4e4' }}>
                <TableCell colSpan={2}><Typography variant='subtitle2'>Wartości odżywcze w 100g</Typography></TableCell>
              </TableRow>
            </TableHead>
            <TableBody id='nutrients-rows'>
              {nutrients && Object.keys(nutrients).map((key) => {
                return isNotEmpty(key) ? (
                  <TableRow hover key={key}>
                    <TableCell>{formatNutrients(key)}</TableCell>
                    <TableCell>{nutrients[key]}</TableCell>
                  </TableRow>
                ) : null;
              })}
              {!nutrients || isEmptyNutrients(nutrients) && <TableRow hover>
                <TableCell colSpan={2} align='center'>Brak danych</TableCell>
              </TableRow>}
            </TableBody>
          </Table>
        </TableContainer>
      );
    };

    const getProductInfo = (productInfo) => (
      <div className={`product-info-container ${productInfo.allergenWarning ? 'allergen-warning' : ''}`}>

        <div className='product-btn-group'>
          <div className='warning-icon'>
            {productInfo.allergenWarning ? <AllergenWarning /> : null}
          </div>
          {authContext.isLoggedIn &&
          <Button
            color='primary'
            variant='outlined'
            onClick={openQuantityDialog}
            title='Dodaj produkt do panelu'
            aria-label='Dodaj produkt do panelu'
            startIcon={<AddCircleIcon />}
          >
            Dodaj produkt do panelu
          </Button>}

          {/*hidden modification dialog*/}
          <DefineProductQuantity quantityProps={quantityProps} apiCall={sendProductToDashboard} showCategory />

        </div>
        <Grid container justifyContent='space-between' alignItems='stretch' style={{ minHeight: '40vh' }}>
          <Grid item xs={12} sm={12} md={12} lg={6}>
            <AutoFitImage frameWidth='90%' frameHeight='90%' imgSize='contain'
              imgSrc={productInfo.images.find(obj => obj.url)?.url ?? ''} />
          </Grid>
          <Grid container xs={12} sm={12} md={12} lg={6}>
            <Grid item className='product-info'>
              <Typography variant='h4' align='center'>
                {productInfo.name}
              </Typography>
              <Typography variant='body1'>
                <div>
                  <br />
                  {productInfo.brands && <><b>Marka: </b>{productInfo.brands}</>}
                  <br />
                  {productInfo.servingSize && <><b>Wielkość porcji: </b> {productInfo.servingSize}</>}
                </div>
              </Typography>
            </Grid>
            <Grid container justify='center' alignItems='center'>
              <Grid item xs={12} md={12} className='ingredients-block'>
                <br />
                <Typography variant='subtitle2'>Składniki</Typography>
                <div>
                  {getIngredients(productInfo?.ingredients)}
                </div>

                <div className='additives'>
                  <Typography variant='subtitle2'>Dodatki</Typography>
                  <div>{getAdditives(productInfo?.ingredients)}</div>
                  <br />
                </div>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
        <Grid container justify='center' alignItems='center' xs={12} md={12}>
          {getNutrients(productInfo.nutrients)}
        </Grid>


      </div>
    );

    return (
      <MainContent>
        {state === undefined ? <CircularProgress color='success' size='5rem' /> : getProductInfo(state)}
      </MainContent>
    );
  }
;

export default ProductInfoPage;
