import { createServer, Model } from 'miragejs';


export function makeServer({ environment = 'development' } = {}) {
  let server = createServer({
    environment,
    models: {
      product: Model,
    },

    seeds(server) {
      server.create('product', {
        name: 'Product #1',
        href: '/products/1',
        imgUrl: 'https://images.pexels.com/photos/326005/pexels-photo-326005.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940',
      });
      server.create('product', {
        name: 'Product #2',
        href: '/products/2',
        imgUrl: 'https://images.pexels.com/photos/326005/pexels-photo-326005.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940',
      });
      server.create('product', {
        name: 'Product #3',
        href: '/products/3',
        imgUrl: 'https://images.pexels.com/photos/326005/pexels-photo-326005.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940',
      });
    },

    routes() {
      this.namespace = 'api';

      this.get('/products', (schema) => {
        return schema.products.all();
      });

      this.post('/reminders', (schema, request) => {
        let attrs = JSON.parse(request.requestBody);
        return schema.products.create(attrs);
      });
    },

  });

  return server;
}
