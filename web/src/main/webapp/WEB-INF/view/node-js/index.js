import express from 'express';
import path from 'path'

const PORT = 3000;
const app = express();
const __dirname = path.resolve();

app.use(express.static(path.resolve(__dirname, '..')))

app.get('/', (req, res) => {
  res.sendFile(path.resolve(__dirname, '..', 'main-page.html'))
})

app.listen(PORT, () => {
  console.log(`Server has been started on ${PORT}`)
});