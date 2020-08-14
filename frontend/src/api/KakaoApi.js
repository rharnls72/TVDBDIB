// From: https://parkjihwan.tistory.com/9
// Only aws
const KakaoApi = {
    Login() {
        window.Kakao.Auth.authorize({
            redirectUri: 'http://i3a106.p.ssafy.io/oauth/kakao'
        });
    }
}
  
export default KakaoApi;