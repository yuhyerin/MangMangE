const express = require("express");
const app = express();
const morgan = require("morgan");
const PORT = 8000;
const cors = require("cors");
const { response, json } = require("express");
const fs = require('fs') // file stream


app.use(morgan("dev"));
app.use(express.json());
app.use(express.urlencoded({extended: false}));
app.use(
    cors({
        origin: true,
        credentials: true
    })
)

app.get("/api/", (req, res) => {
    res.writeHead(200, {'Content-Type': 'video/mp4'})
    var rs = fs.createReadStream('video.mp4');
    rs.pipe(res);
});

app.listen(PORT, () => console.log(`This server listening on ${PORT}`));