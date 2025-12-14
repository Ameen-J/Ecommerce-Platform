import { useEffect, useState } from "react";
import { getAllProducts } from "../services/productService";
import ProductCard from "../components/ProductCard";
import "../styles/Home.css";

function Home() {
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async () => {
    try {
      const data = await getAllProducts();
      setProducts(data);
    } catch (err) {
      console.log("Failed to load products");
    }
  };

  const filteredProducts = products.filter(p =>
    p.name.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="home-container">
      <div className="search-bar">
        <input
          type="text"
          placeholder="Search products..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      <div className="product-grid">
        {filteredProducts.length > 0 ? (
          filteredProducts.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))
        ) : (
          <p>No products found.</p>
        )}
      </div>
    </div>
  );
}

export default Home;
