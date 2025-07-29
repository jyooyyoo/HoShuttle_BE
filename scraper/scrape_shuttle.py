import requests
from bs4 import BeautifulSoup
import json
import re

# 1. 스크래핑할 웹 페이지 URL
url = "http://www.hoseo.ac.kr/Home/Contents.mbz?action=MAPP_2302082206"
output_file_path = "ShuttleScheduleDB.json"

def fetch_html(url):
    """지정된 URL에서 HTML 내용을 가져옵니다."""
    try:
        response = requests.get(url)
        response.raise_for_status() # HTTP 오류가 발생하면 예외 발생
        # 페이지 인코딩이 명시되지 않은 경우, euc-kr일 가능성이 높으므로 명시적으로 설정
        response.encoding = 'euc-kr'
        return response.text
    except requests.exceptions.RequestException as e:
        print(f"URL을 가져오는 중 오류 발생: {e}")
        return None

def parse_timetable(html):
    """HTML에서 셔틀 시간표 데이터를 파싱합니다."""
    soup = BeautifulSoup(html, 'html.parser')

    # 웹 페이지에서 "천안 ↔ 아산 캠퍼스 셔틀버스 운행 시간표"와
    # "아산 ↔ 천안 캠퍼스 셔틀버스 운행 시간표"를 포함하는 h4 태그를 찾고,
    # 그 다음 오는 table 태그를 찾아서 시간표를 구분합니다.

    shuttle_data = {
        "cheonan_to_asan": [],
        "asan_to_cheonan": []
    }

    # "천안 ↔ 아산" 시간표 추출
    cheonan_to_asan_header = soup.find('h4', string=re.compile(r'천안\s*↔\s*아산\s*캠퍼스\s*셔틀버스\s*운행\s*시간표'))
    if cheonan_to_asan_header:
        table = cheonan_to_asan_header.find_next_sibling('table', class_='normal_table')
        if table:
            shuttle_data["cheonan_to_asan"] = extract_table_data(table)
            print("천안 ↔ 아산 시간표 추출 완료.")
        else:
            print("천안 ↔ 아산 시간표 테이블을 찾을 수 없습니다.")
    else:
        print("천안 ↔ 아산 시간표 제목을 찾을 수 없습니다.")

    # "아산 ↔ 천안" 시간표 추출
    asan_to_cheonan_header = soup.find('h4', string=re.compile(r'아산\s*↔\s*천안\s*캠퍼스\s*셔틀버스\s*운행\s*시간표'))
    if asan_to_cheonan_header:
        table = asan_to_cheonan_header.find_next_sibling('table', class_='normal_table')
        if table:
            shuttle_data["asan_to_cheonan"] = extract_table_data(table)
            print("아산 ↔ 천안 시간표 추출 완료.")
        else:
            print("아산 ↔ 천안 시간표 테이블을 찾을 수 없습니다.")
    else:
        print("아산 ↔ 천안 시간표 제목을 찾을 수 없습니다.")

    return shuttle_data

def extract_table_data(table):
    """하나의 테이블에서 시간표 데이터를 추출합니다."""
    rows = table.find_all('tr')
    if not rows:
        return []

    # 첫 번째 행은 헤더 (정류장 이름)
    # th 태그와 td 태그를 모두 고려 (간혹 td로 헤더가 올 수도 있음)
    headers = [cell.get_text(strip=True) for cell in rows[0].find_all(['th', 'td'])]

    # "구분" 또는 "운행구분"과 같은 불필요한 헤더는 제거
    headers = [h for h in headers if h not in ["구분", "운행구분"]]

    timetable_list = []

    # 나머지 행은 각 셔틀의 시간표
    for row in rows[1:]: # 헤더 제외
        cols = row.find_all('td')
        if not cols:
            continue

        # 첫 번째 TD가 '평일', '주말' 등의 '구분' 값일 가능성이 높음
        # 실제 시간 데이터는 그 다음 TD부터 시작
        # 그러나 HTML 구조에 따라 달라질 수 있으므로, 헤더 개수와 TD 개수를 비교

        current_shuttle_times = {}

        # 유효한 시간 데이터가 있는 TD만 추출.
        # 첫 번째 TD가 구분자(예: 평일)일 경우를 대비하여 시작 인덱스를 조정
        start_col_idx = 0
        if len(cols) > len(headers): # TD가 헤더보다 많다면 첫 TD는 구분자일 가능성
             start_col_idx = 1

        for header_idx, header_name in enumerate(headers):
            col_idx_in_row = start_col_idx + header_idx
            if col_idx_in_row < len(cols):
                time_str = cols[col_idx_in_row].get_text(strip=True)

                # "08:00" 같은 유효한 시간 형식만 저장
                # 비어 있거나 "운행 안함" 같은 텍스트는 제외
                if re.match(r'^\d{2}:\d{2}$', time_str):
                    current_shuttle_times[header_name] = time_str
                # else:
                #     if time_str: # 디버깅용: 유효하지 않은 시간 형식 확인
                #         print(f"DEBUG: '{header_name}'에 유효하지 않은 시간 형식 또는 빈 값: '{time_str}'")

        if current_shuttle_times:
            timetable_list.append(current_shuttle_times)

    return timetable_list

# --- 메인 실행 ---
if __name__ == "__main__":
    html_content = fetch_html(url)

    if html_content:
        print("HTML 내용을 성공적으로 가져왔습니다. 파싱을 시작합니다.")
        parsed_data = parse_timetable(html_content)

        if parsed_data["cheonan_to_asan"] or parsed_data["asan_to_cheonan"]:
            print("\n데이터 파싱 완료:")
            # print(json.dumps(parsed_data, indent=2, ensure_ascii=False)) # 디버깅용 출력

            with open(output_file_path, 'w', encoding='utf-8') as f:
                json.dump(parsed_data, f, indent=2, ensure_ascii=False)
            print(f"\n데이터가 '{output_file_path}' 파일에 성공적으로 저장되었습니다.")
        else:
            print("\n시간표 데이터를 찾을 수 없습니다. HTML 구조가 변경되었을 수 있습니다.")
    else:
        print("HTML 내용을 가져오지 못했습니다. 스크립트를 종료합니다.")