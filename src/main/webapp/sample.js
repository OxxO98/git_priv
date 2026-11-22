// --- 設定 ---
// ご自身のRapidAPIのAPIキーに置き換えてください
const apiKey = 'fd30365a39msh90c563bda789409p107aafjsn8e42a041bcd7';
const spotApiHost = 'navitime-spot.p.rapidapi.com';
const mapApiHost = 'navitime-maps.p.rapidapi.com';

// --- DOM要素の取得 ---
const keywordInput = document.getElementById('keywordInput');
const searchButton = document.getElementById('searchButton');
const mapContainer = document.getElementById('map-container');
const resultArea = document.getElementById('resultArea');

// 地図オブジェクト
let map = null;
let pin = null;

// Maps APIのJavaScriptファイルを動的に読み込む
const mapScriptUrl = `https://navitime-maps.p.rapidapi.com/map_script?host=localhost`;


/**
 * エラーを表示するヘルパー関数
 * @param {Error} error - エラーオブジェクト
 */
function displayError(error) {
    resultArea.textContent = `エラーが発生しました: \n${error.message}`;
}

// fetch APIまたはscriptタグを使って地図APIのスクリプトを読み込む
async function loadMapScript() {
    // ヘッダー付きでスクリプトを読み込む
    options = {
        method: 'GET',
        headers: {
            'x-rapidapi-host': mapApiHost,
            'x-rapidapi-key': apiKey
        }
    };

    try {
        const response = await fetch(mapScriptUrl, options);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const scriptContent = await response.text();

        // スクリプト内容を動的に実行
        const script = document.createElement('script');
        script.textContent = scriptContent;
        document.head.appendChild(script);

        // スクリプトが実行されたら地図を初期化
        setTimeout(() => {
            initializeMap();
        }, 100);
    } catch (error) {
        console.error('地図スクリプトの読み込みに失敗しました:', error);
    }
}

function initializeMap() {
    // 地図を初期化（スクリプト読み込み後に呼び出される）
    map = new navitime.geo.Map('map-container', new navitime.geo.LatLng(35.681236, 139.767125), 16);

    // 地図の中心座標を取得
    const center = map.getCenter();

    // 中心点にマーカーを生成
    // マーカーの画像はご自身でご用意ください
    pin = new navitime.geo.overlay.Pin({
        icon : './pin.png',
        position: center,
        draggable: true,
        map: map
    });

    // マーカーを表示
    pin.setVisible(true);
}

// 地図スクリプトを読み込み開始
loadMapScript();

// スポット検索を実行する
async function handleSpotSearch() {
    const keyword = keywordInput.value;
    if (!keyword) {
        alert('キーワードを入力してください。');
        return;
    }

    // 地図が初期化されているかチェック
    if (!map) {
        alert('地図が読み込み中です。しばらくお待ちください。');
        return;
    }

    // マーカーの位置を取得
    const position = pin.getPosition();
    // const url = `https://navitime-spot.p.rapidapi.com/spot/category_code?category=07`;
    const url = `https://navitime-spot.p.rapidapi.com/spot?word=${encodeURIComponent(keyword)}&category_filter=07`;
    const options = {
        method: 'GET',
        headers: {
            'x-rapidapi-host': spotApiHost,
            'x-rapidapi-key': apiKey
        }
    };

    try {
        const response = await fetch(url, options);

        // await category_list();
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        // 結果を表示
        // console.log(data.items.reduce( (acc, cur) => acc + cur.count, 0 ))
        console.log(data.items)
        displaySpotList(data.items);

    } catch (error) {
        displayError(error);
    }
}

// スポットリストを表示するヘルパー関数
function displaySpotList(items) {
    spotListArea.innerHTML = ''; // リストをクリア

    if (items && items.length > 0) {
        items.forEach(spot => {
            const spotDiv = document.createElement('div');
            spotDiv.classList.add('spot-item');

            const nameSpan = document.createElement('div');
            nameSpan.classList.add('spot-name');
            nameSpan.textContent = spot.name;
            spotDiv.appendChild(nameSpan);

            const addressSpan = document.createElement('div');
            addressSpan.classList.add('spot-address');
            addressSpan.textContent = spot.address_name || '住所情報なし';
            spotDiv.appendChild(addressSpan);

            if (spot.phone) {
                const phoneSpan = document.createElement('div');
                phoneSpan.classList.add('spot-phone');
                phoneSpan.textContent = `電話番号: ${spot.phone}`;
                spotDiv.appendChild(phoneSpan);
            }

            spotListArea.appendChild(spotDiv);
        });
    } else {
        spotListArea.innerHTML = '<p>該当するスポットが見つかりませんでした。</p>';
    }
}

async function category_list(){
    console.log('category_list');

    const url = `https://navitime-spot.p.rapidapi.com/category_list`;
    const options = {
        method: 'GET',
        headers: {
            'x-rapidapi-host': spotApiHost,
            'x-rapidapi-key': apiKey
        }
    };

    try {
        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();

        // 結果を表示
        console.log(data.items);

    } catch (error) {
        displayError(error);
    }
}

// イベントリスナーの設定
searchButton.addEventListener('click', handleSpotSearch);