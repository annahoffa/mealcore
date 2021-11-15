import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import apiCall from '../../utils/apiCall';
import { Grid, Tooltip, Typography } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import Skeleton from '@material-ui/lab/Skeleton';
import MainContent from '../../components/main-content/main-content.component';
import AllergenWarning from '../../components/allergen-warning/allergen-warning.component';
import DefineProductQuantity from '../../components/define-product-quantity/define-product-quantity.component';

import './product-info.styles.scss';


const ProductInfoPage = (props) => {
    const productId = props.match.params.id; // query extracted from the browser's url field
    let history = useHistory();
    const [state, setState] = useState();

    useEffect(() => {
      apiCall(`/api/products/findById?productId=${productId}`)
      .then(data => setState(data))
      .catch(error => console.log(error));
    }, []);

    const sendProductToDashboard = () => {
      apiCall(`/api/user/addProduct?productId=${productId}&quantity=${productQuantity}`, {
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
    };

    //--- Quantity modification dialog ---

    const [productQuantity, setProductQuantity] = useState('');

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
      function formatNutrients(data) {
        if(data.size < 1)
          return data;
        else
          return (data.charAt(0).toUpperCase() + data.slice(1)).replace('_', ' ');
      }

      return (
        <div>
          {nutrients
            ?
            (nutrients && Object.keys(nutrients).map((key) => {
              const value = nutrients[key];

              if(['id', 'productId'].includes(key)) {
                return null;
              }
              if(value === '' || value === null) {
                return null;
              }
              return (
                <div>
                  {formatNutrients(key)} &nbsp; {value}
                </div>
              );
            }))
            :
            <span>Brak danych</span>}
        </div>
      );
    };

    const getProductInfo = (productInfo) => (
      <div className={`product-info-container ${productInfo.allergenWarning ? 'allergen-warning' : ''}`}>

        <div className='product-btn-group'>
          <div className='warning-icon'>
            {productInfo.allergenWarning ? <AllergenWarning /> : null}
          </div>
          <IconButton color='primary' onClick={openQuantityDialog} title='Add product to dashboard'
            aria-label='Dodaj produkt do panelu'>
            <AddCircleIcon />
          </IconButton>

          {/*hidden modification dialog*/}
          <DefineProductQuantity quantityProps={quantityProps} apiCall={sendProductToDashboard} />

        </div>

        <Grid container align='center' justify='center' alignItems='center'>
          <Grid item xs={12} md={4}>
            <div className='photo-container'>
              <img src={productInfo.images.find(obj => obj.url)?.url ?? ''} alt='Product' />
            </div>
          </Grid>
          <Grid item xs={12} md={8} className='product-info'>
            <Typography variant='h6' align='center'>
              {productInfo.name}
            </Typography>
            <Typography variant='body2'>
              Marka: {productInfo.brands || '\u2014'}
              <br />
              Wielkość porcji: {productInfo.servingSize || '\u2014'}
            </Typography>
          </Grid>
        </Grid>

        <Grid container justify='center' alignItems='center'>
          <Grid item xs={12} md={12} className='ingredients-block'>
            <br />
            <Typography variant='subtitle2'>Składniki:</Typography>
            <div>
              {getIngredients(productInfo?.ingredients)}
            </div>

            <div className='additives'>
              <Typography variant='subtitle2'>Dodatki:</Typography>
              <div>{getAdditives(productInfo?.ingredients)}</div>
              <br />
            </div>
          </Grid>
        </Grid>

        <Grid container justify='center' alignItems='center'>
          <Grid item xs={12} md={12} className='nutrients-block'>
            <Typography variant='subtitle2'>Wartości odżywcze w 100g:</Typography>
            <br />
            <div>
              {getNutrients(productInfo.nutrients)}
            </div>
          </Grid>
        </Grid>

      </div>
    );

    return (
      <MainContent>
        {state === undefined ? <Skeleton /> : getProductInfo(state)}
      </MainContent>
    );
  }
;

export default ProductInfoPage;
