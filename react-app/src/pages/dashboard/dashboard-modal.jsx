import { Button, InputBase, makeStyles, Typography } from '@material-ui/core';
import Skeleton from '@material-ui/lab/Skeleton';
import ItemsGrid from '../../components/response-items-grid/response-items-grid.component';
import Modal from '@material-ui/core/Modal';
import React, { useContext, useState } from 'react';
import { DashboardProviderContext } from './dashboard-provider';
import SearchIcon from '@material-ui/icons/Search';
import ProductInfo from '../../components/product-info/product-info.component';


const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'grid',
    width: '80vw',
    height: '80vh',
    margin: 'auto',
    overflow: 'auto',
    gridTemplateRows: '0 1fr',
  },
  paper: {
    height: 'fit-content',
    backgroundColor: theme.palette.background.paper,
    border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
    minHeight: '100%',
  },
}));

const DashboardModal = ({ open, setOpen }) => {
  const { paper, modal } = useStyles();
  const [searchValue, setSearchValue] = useState('');
  const [selectedItem, setSelectedItem] = useState(null);
  const {
    searchProductMutation,
    date,
    userProductsQuery,
  } = useContext(DashboardProviderContext);

  const handleSearchProduct = () => {
    searchProductMutation.mutate(searchValue);
  };

  return (
    <Modal className={modal + ' dashboard-modal'} open={open} onClose={() => {
      setOpen(false);
      userProductsQuery.refetch();
      setSearchValue('')
      setSelectedItem(null)
      searchProductMutation.reset()
    }}>
      {selectedItem ?
        <div className={paper}>
          <ProductInfo id={selectedItem} handleClose={() => {
            setSelectedItem(null);
          }} date={date} />
        </div>
        :
        <div className={paper}>
          <Typography variant='h5' align='center'>Jakiego produktu aktualnie poszukujesz?</Typography>
          <div className='search'>
            <div className='icon-container'>
              <SearchIcon />
            </div>
            <InputBase
              value={searchValue}
              onChange={(e) => setSearchValue(e.target.value)}
              type='search'
              className='search-input'
              placeholder='Wyszukaj...'
              onKeyDown={(e) => {
                if(e.key === 'Enter') {
                  handleSearchProduct();
                }
              }}
              inputProps={{ 'aria-label': 'search' }}
            />
            <Button variant='contained' color='primary' onClick={handleSearchProduct}>Wyszukaj</Button>
          </div>

          {(() => {
            switch(searchProductMutation.status) {
              case 'idle':
                break;
              case 'loading':
                return <Skeleton />;
              case 'success':
                return (
                  <>
                    <h2>Wynik wyszukiwania:</h2>
                    <ItemsGrid items={searchProductMutation.data} onSelect={setSelectedItem} />
                  </>
                );
            }
            return null;
          })()}
        </div>}
    </Modal>
  );
};

export default DashboardModal;