export class Consts{
  static readonly API_PATH: string = 'http://localhost:8000/api/presto';
  static readonly API_GET_AVAILABLE_DBS: string = '/getAvailableDbs';
  static readonly API_GET_QUERY_RESULTS: string = '/getQueryResults';
  static readonly API_GET_AVAILABLE_DB_SCHEMAS: string = '/getAvailableDbSchemas';
  static readonly API_GET_AVAILABLE_SCHEMA_TABLES: string = '/getAvailableSchemaTables';
  static readonly API_GET_COLUMNS_FROM_TABLE: string = '/getColumnsFromTable';
  static readonly API_FILTER_OPERATIONS: string = '/getFilterOperations';
}
